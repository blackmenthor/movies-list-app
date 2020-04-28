package tech.arifandi.movielistapp.ui.movie_review_list

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import tech.arifandi.movielistapp.redux.ThreadSafeStore
import tech.arifandi.movielistapp.redux.actions.MovieReviewListActions
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.base.BasePresenter
import tech.arifandi.movielistapp.ui.movie_review_list.converters.AppStateToMovieReviewListViewModelConverter

internal class MovieReviewListPresenter(
    private val movieDetailContract: MovieReviewListContract,
    private val store: ThreadSafeStore,
    stateFlowable: Flowable<AppState>,
    converter: AppStateToMovieReviewListViewModelConverter
): BasePresenter() {

    private val compositeDisposable = CompositeDisposable()

    val model: Flowable<MovieReviewListViewModel> = stateFlowable
        .distinctUntilChanged(::checkViewModel)
        .map(converter::convert)

    private fun checkViewModel(oldState: AppState, newState: AppState): Boolean {
        return oldState.movieReviewListState.currentState == newState.movieReviewListState.currentState
                && oldState.movieReviewListState.reviews == newState.movieReviewListState.reviews
    }

    fun startFetching(movieId: Int) = store.dispatch(MovieReviewListActions.StartFetching(movieId))

    fun loadNextPage() = store.dispatch(MovieReviewListActions.LoadNextPage)

    fun destroy() {
        compositeDisposable.clear()
        store.dispatch(MovieReviewListActions.ResetState)
    }

}