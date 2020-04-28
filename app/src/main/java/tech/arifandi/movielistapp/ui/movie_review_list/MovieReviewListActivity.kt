package tech.arifandi.movielistapp.ui.movie_review_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_review_list.containerError
import kotlinx.android.synthetic.main.activity_review_list.containerLoading
import kotlinx.android.synthetic.main.activity_review_list.resultsList
import kotlinx.android.synthetic.main.error_view.*
import tech.arifandi.movielistapp.App
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.api.error.ApiError
import tech.arifandi.movielistapp.models.MovieReview
import tech.arifandi.movielistapp.ui.base.BaseActivity
import tech.arifandi.movielistapp.ui.movie_detail.adapter.ReviewAdapter
import tech.arifandi.movielistapp.ui.movie_review_list.di.MovieReviewListGraph
import tech.arifandi.movielistapp.ui.movie_review_list.di.MovieReviewListModule
import tech.arifandi.movielistapp.utils.SchedulerProvider
import tech.arifandi.movielistapp.utils.asVisibility
import javax.inject.Inject

internal sealed class MovieReviewListViewState {
    object Idle: MovieReviewListViewState()
    object Requesting: MovieReviewListViewState()
    data class Failed(val cause: Throwable): MovieReviewListViewState()
    object Succeed: MovieReviewListViewState()
}

internal data class MovieReviewListViewModel(
    val viewState: MovieReviewListViewState,
    val reviews: List<MovieReview?>
)

internal class MovieReviewListActivity : BaseActivity(), MovieReviewListContract {

    @Inject
    lateinit var presenter: MovieReviewListPresenter

    @Inject
    lateinit var adapter: ReviewAdapter

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    private val compositeDisposable = CompositeDisposable()

    override fun inject() {
        MovieReviewListGraph()
            .sampleAppComponent((application as App).graph)
            .movieReviewListModule(MovieReviewListModule(this))
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_list)
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
        val title = resources.getString(R.string.movie_review_list_title)
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
        resultsList.visibility = View.GONE
        btnRetry.setOnClickListener { startPresenter() }

        if (throwable is ApiError)
            txtError.text = throwable.getMessage(resources)
        else
            txtError.text = resources.getString(R.string.api_connectivity_error)
    }

    private fun showAccordingToState(viewModel: MovieReviewListViewModel) {
        when(viewModel.viewState) {
            MovieReviewListViewState.Idle -> {
                containerError.visibility = View.GONE
                containerLoading.visibility = View.GONE
                resultsList.visibility = View.GONE
            }
            MovieReviewListViewState.Requesting -> {
                containerError.visibility = View.GONE
                containerLoading.visibility = View.VISIBLE
                resultsList.visibility = View.GONE
            }
            is MovieReviewListViewState.Failed -> showErrorState(viewModel.viewState.cause)
            MovieReviewListViewState.Succeed -> {
                containerLoading.visibility = View.GONE
                resultsList.visibility = View.VISIBLE
                containerError.visibility = View.GONE
                resultsList.visibility = viewModel.reviews?.isNotEmpty()?.asVisibility() ?: View.GONE
            }
        }
        adapter.results = viewModel.reviews
    }

    private fun setupRecyclerView() {
        resultsList.adapter = adapter
        resultsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        resultsList.isNestedScrollingEnabled = false

        adapter.onLoadingViewDrawn = { presenter.loadNextPage() }
    }

    companion object {

        private const val MOVIE_ID_KEY = "MOVIE"

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieReviewListActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, movieId)
            return intent
        }
    }

}