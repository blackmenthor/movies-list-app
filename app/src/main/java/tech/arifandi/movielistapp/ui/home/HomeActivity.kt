package tech.arifandi.movielistapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.error_view.*
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.App
import tech.arifandi.movielistapp.api.error.ApiError
import tech.arifandi.movielistapp.models.Genre
import tech.arifandi.movielistapp.ui.base.BaseActivity
import tech.arifandi.movielistapp.ui.genre_detail.GenreDetailActivity
import tech.arifandi.movielistapp.ui.home.adapter.GenresAdapter
import tech.arifandi.movielistapp.ui.home.di.HomeGraph
import tech.arifandi.movielistapp.ui.home.di.HomeModule
import tech.arifandi.movielistapp.utils.SchedulerProvider
import tech.arifandi.movielistapp.utils.asVisibility
import javax.inject.Inject

internal sealed class HomeViewState {
    object Idle: HomeViewState()
    object Requesting: HomeViewState()
    data class Failed(val cause: Throwable): HomeViewState()
    object Succeed: HomeViewState()
}

internal data class HomeViewModel(val viewState: HomeViewState, val results: List<Genre?>)

internal class HomeActivity : BaseActivity(), HomeContract {

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var adapter: GenresAdapter

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    private val compositeDisposable = CompositeDisposable()

    override fun inject() {
        HomeGraph()
            .sampleAppComponent((application as App).graph)
            .homeModule(HomeModule(this))
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupRecyclerView()
        subscribeToPresenter()
        presenter.startFetchingGenres()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.destroy()
        compositeDisposable.clear()
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
        containerMain.visibility = View.GONE
        btnRetry.setOnClickListener { presenter.startFetchingGenres() }

        if (throwable is ApiError)
            txtError.text = throwable.getMessage(resources)
        else
            txtError.text = resources.getString(R.string.api_connectivity_error)
    }

    private fun showAccordingToState(viewModel: HomeViewModel) {
        when(viewModel.viewState) {
            HomeViewState.Idle -> {
                containerError.visibility = View.GONE
                containerLoading.visibility = View.GONE
                containerMain.visibility = View.GONE
            }
            HomeViewState.Requesting -> {
                containerError.visibility = View.GONE
                containerLoading.visibility = View.VISIBLE
                containerMain.visibility = View.GONE
            }
            is HomeViewState.Failed -> showErrorState(viewModel.viewState.cause)
            HomeViewState.Succeed -> {
                containerLoading.visibility = View.GONE
                containerMain.visibility = viewModel.results.isNotEmpty().asVisibility()
                containerError.visibility = viewModel.results.isEmpty().asVisibility()
            }
        }
        adapter.results = viewModel.results
    }

    private fun setupRecyclerView() {
        resultsList.adapter = adapter
        resultsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        resultsList.isNestedScrollingEnabled = false

        adapter.onGenreClicked = { presenter.onGenreClicked(it) }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun goToGenreDetailActivity(genre: Genre) {
        val intent = GenreDetailActivity.newIntent(this, genre)
        startActivity(intent)
    }

}