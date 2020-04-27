package tech.arifandi.movielistapp.ui.genre_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_genre_detail.*
import kotlinx.android.synthetic.main.error_view.*
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.App
import tech.arifandi.movielistapp.api.error.ApiError
import tech.arifandi.movielistapp.models.Movie
import tech.arifandi.movielistapp.ui.base.BaseActivity
import tech.arifandi.movielistapp.ui.genre_detail.adapter.GenreDetailAdapter
import tech.arifandi.movielistapp.ui.genre_detail.di.GenreDetailGraph
import tech.arifandi.movielistapp.ui.genre_detail.di.GenreDetailModule
import tech.arifandi.movielistapp.ui.movie_detail.MovieDetailActivity
import tech.arifandi.movielistapp.utils.SchedulerProvider
import tech.arifandi.movielistapp.utils.asVisibility
import javax.inject.Inject

internal sealed class GenreDetailViewState {
    object Idle: GenreDetailViewState()
    object Requesting: GenreDetailViewState()
    data class Failed(val cause: Throwable): GenreDetailViewState()
    object Succeed: GenreDetailViewState()
}

internal data class GenreDetailViewModel(val viewState: GenreDetailViewState, val results: List<Movie?>)

internal class GenreDetailActivity : BaseActivity(), GenreDetailView {

    @Inject
    lateinit var presenter: GenreDetailPresenter

    @Inject
    lateinit var adapter: GenreDetailAdapter

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    private val compositeDisposable = CompositeDisposable()

    override fun inject() {
        GenreDetailGraph()
            .sampleAppComponent((application as App).graph)
            .genreDetailModule(GenreDetailModule(this))
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_detail)
        setupRecyclerView()
        subscribeToPresenter()
        startPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.destroy()
        compositeDisposable.clear()
    }

    private fun startPresenter() {
        val genreId = intent.getIntExtra(GENRE_ID_KEY, 0)
        presenter.startFetchingGenres(genreId)
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

        if (throwable is ApiError)
            txtError.text = throwable.getMessage(resources)
        else
            txtError.text = resources.getString(R.string.api_connectivity_error)
    }

    private fun showAccordingToState(viewModel: GenreDetailViewModel) {
        when(viewModel.viewState) {
            GenreDetailViewState.Idle -> {
                containerError.visibility = View.GONE
                containerLoading.visibility = View.GONE
                resultsList.visibility = View.GONE
            }
            GenreDetailViewState.Requesting -> {
                containerError.visibility = View.GONE
                containerLoading.visibility = View.VISIBLE
                resultsList.visibility = View.GONE
            }
            is GenreDetailViewState.Failed -> showErrorState(viewModel.viewState.cause)
            GenreDetailViewState.Succeed -> {
                containerLoading.visibility = View.GONE
                resultsList.visibility = viewModel.results.isNotEmpty().asVisibility()
                containerError.visibility = viewModel.results.isEmpty().asVisibility()
            }
        }
        adapter.results = viewModel.results
    }

    private fun setupRecyclerView() {
        resultsList.adapter = adapter
        resultsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.onMovieClicked = { presenter.onMovieClicked(it) }
        adapter.onLoadingViewDrawn = { presenter.loadNextPage() }
    }

    companion object {

        private const val GENRE_ID_KEY = "GENRE"

        fun newIntent(context: Context, genreId: Int): Intent {
            val intent = Intent(context, GenreDetailActivity::class.java)
            intent.putExtra(GENRE_ID_KEY, genreId)
            return intent
        }
    }

    override fun openMovieDetailPage(movieId: Int) {
        val intent = MovieDetailActivity.newIntent(this, movieId)
        startActivity(intent)
    }

}