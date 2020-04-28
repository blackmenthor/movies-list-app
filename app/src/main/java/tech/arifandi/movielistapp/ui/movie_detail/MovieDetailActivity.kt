package tech.arifandi.movielistapp.ui.movie_detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.movie_detail_content.*
import tech.arifandi.movielistapp.App
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.api.error.ApiError
import tech.arifandi.movielistapp.models.MovieDetail
import tech.arifandi.movielistapp.models.MovieReview
import tech.arifandi.movielistapp.models.MovieVideo
import tech.arifandi.movielistapp.ui.base.BaseActivity
import tech.arifandi.movielistapp.ui.movie_detail.adapter.ReviewAdapter
import tech.arifandi.movielistapp.ui.movie_detail.adapter.VideoAdapter
import tech.arifandi.movielistapp.ui.movie_detail.di.MovieDetailGraph
import tech.arifandi.movielistapp.ui.movie_detail.di.MovieDetailModule
import tech.arifandi.movielistapp.ui.movie_review_list.MovieReviewListActivity
import tech.arifandi.movielistapp.utils.SchedulerProvider
import tech.arifandi.movielistapp.utils.asVisibility
import tech.arifandi.movielistapp.utils.convertToImagePath
import javax.inject.Inject

internal sealed class MovieDetailViewState {
    object Idle: MovieDetailViewState()
    object Requesting: MovieDetailViewState()
    data class Failed(val cause: Throwable): MovieDetailViewState()
    object Succeed: MovieDetailViewState()
}

internal data class MovieDetailViewModel(
    val viewState: MovieDetailViewState,
    val result: MovieDetail?,
    val reviews: List<MovieReview>?,
    val moreReviewsAvailable: Boolean,
    val videos: List<MovieVideo>?
)

internal class MovieDetailActivity : BaseActivity(), MovieDetailContract {

    @Inject
    lateinit var presenter: MovieDetailPresenter

    @Inject
    lateinit var adapter: ReviewAdapter

    @Inject
    lateinit var videoAdapter: VideoAdapter

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    private val compositeDisposable = CompositeDisposable()

    override fun inject() {
        MovieDetailGraph()
            .sampleAppComponent((application as App).graph)
            .movieDetailModule(MovieDetailModule(this))
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setUpActionBar()
        subscribeToPresenter()
        setupRecyclerView()
        startPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.destroy()
        compositeDisposable.clear()
    }

    private fun setUpActionBar() {
        val title = resources.getString(R.string.movie_detail_title)
        supportActionBar?.title = title
    }

    private fun startPresenter() {
        val genreId = intent.getIntExtra(MOVIE_ID_KEY, 0)
        presenter.startFetching(genreId)
    }

    private fun subscribeToPresenter() {
        compositeDisposable.add(
            presenter
                .model
                .observeOn(schedulerProvider.mainThread())
                .subscribe(::showAccordingToState, ::showErrorState)
        )
    }

    private fun showErrorState(throwable: Throwable) {
        containerError.visibility = View.VISIBLE
        containerLoading.visibility = View.GONE
        result.visibility = View.GONE
        btnRetry.setOnClickListener { startPresenter() }

        if (throwable is ApiError)
            txtError.text = throwable.getMessage(resources)
        else
            txtError.text = resources.getString(R.string.api_connectivity_error)
    }

    private fun showAccordingToState(viewModel: MovieDetailViewModel) {
        when(viewModel.viewState) {
            MovieDetailViewState.Idle -> {
                containerError.visibility = View.GONE
                containerLoading.visibility = View.GONE
                result.visibility = View.GONE
            }
            MovieDetailViewState.Requesting -> {
                containerError.visibility = View.GONE
                containerLoading.visibility = View.VISIBLE
                result.visibility = View.GONE
            }
            is MovieDetailViewState.Failed -> showErrorState(viewModel.viewState.cause)
            MovieDetailViewState.Succeed -> {
                containerLoading.visibility = View.GONE
                result.visibility = View.VISIBLE
                containerError.visibility = View.GONE
                showMovieContent(viewModel.result)
                txtNoReview.visibility = viewModel.reviews.isNullOrEmpty().asVisibility()
                reviewsList.visibility = viewModel.reviews?.isNotEmpty()?.asVisibility() ?: View.GONE
                viewModel.moreReviewsAvailable.apply {
                    btnSeeAllReviews.visibility = this.asVisibility()
                    if (this) setupSeeAllReviewsBtn()
                }
                txtNoVideos.visibility = viewModel.videos.isNullOrEmpty().asVisibility()
                videosList.visibility = viewModel.videos?.isNotEmpty()?.asVisibility() ?: View.GONE
            }
        }
        adapter.results = viewModel.reviews
        videoAdapter.results = viewModel.videos
    }

    private fun setupSeeAllReviewsBtn() {
        val movieId = intent.getIntExtra(MOVIE_ID_KEY, 0)
        btnSeeAllReviews.setOnClickListener { presenter.goToReviewsPage(movieId) }
    }

    private fun setupRecyclerView() {
        reviewsList.adapter = adapter
        reviewsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        reviewsList.isNestedScrollingEnabled = false

        videosList.adapter = videoAdapter
        videosList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        videosList.isNestedScrollingEnabled = false

        videoAdapter.onOpenYoutubeClicked = { presenter.openYoutubeVideo(it.key) }
    }

    private fun showMovieContent(item: MovieDetail?) {
        item ?: return
        showImage(item.posterPath)
        txtTitle.text = item.title
        txtReleaseDate.text = item.releaseDate
        txtRating.text = item.voteAvg.toString()
        txtRuntime.text = resources.getString(R.string.movie_detail_runtime_template, item.runtime)
        txtOverview.text = item.overview
        reviewsList.visibility = View.VISIBLE
    }

    private fun hideImage() {
        progressBarImg.visibility = View.VISIBLE
        imgPoster.visibility = View.GONE
    }

    private fun showImage() {
        progressBarImg.visibility = View.GONE
        imgPoster.visibility = View.VISIBLE
    }

    private fun showImage(imagePath: String?) {
        imagePath ?: return
        hideImage()
        Picasso
            .get()
            .load(imagePath.convertToImagePath())
            .error(R.drawable.placeholder_error)
            .into(imgPoster, object: Callback {
                override fun onSuccess()  = showImage()

                override fun onError(e: Exception?) = showImage()
            })
    }

    override fun openYoutubeVideo(videoKey: String?) {
        videoKey ?: return
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://$videoKey")))
    }

    override fun goToReviewPage(movieId: Int) {
        val intent = MovieReviewListActivity.newIntent(this, movieId)
        startActivity(intent)
    }

    companion object {

        private const val MOVIE_ID_KEY = "MOVIE"

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, movieId)
            return intent
        }
    }

}