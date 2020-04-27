package tech.arifandi.movielistapp.ui.movie_detail

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import tech.arifandi.movielistapp.redux.ThreadSafeStore
import tech.arifandi.movielistapp.redux.actions.MovieDetailActions
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.base.BasePresenter
import tech.arifandi.movielistapp.ui.movie_detail.converters.AppStateToMovieDetailViewModelConverter

internal class MovieDetailPresenter(
    private val movieDetailView: MovieDetailView,
    private val store: ThreadSafeStore,
    stateFlowable: Flowable<AppState>,
    converter: AppStateToMovieDetailViewModelConverter
): BasePresenter() {

    private val compositeDisposable = CompositeDisposable()

    val model: Flowable<MovieDetailViewModel> = stateFlowable
        .distinctUntilChanged(::checkViewModel)
        .map(converter::convert)

    private fun checkViewModel(oldState: AppState, newState: AppState): Boolean {
        return oldState.movieDetailState.currentState == newState.movieDetailState.currentState
                && oldState.movieDetailState.movie == newState.movieDetailState.movie
                && oldState.movieDetailState.reviews == newState.movieDetailState.reviews
    }

    fun startFetching(movieId: Int) = store.dispatch(MovieDetailActions.StartFetching(movieId))

    fun loadNextPage() = store.dispatch(MovieDetailActions.LoadNextReviewPage)

    fun destroy() {
        compositeDisposable.clear()
        store.dispatch(MovieDetailActions.ResetState)
    }

}