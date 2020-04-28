package tech.arifandi.movielistapp.ui.genre_detail

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import tech.arifandi.movielistapp.models.Movie
import tech.arifandi.movielistapp.redux.ThreadSafeStore
import tech.arifandi.movielistapp.redux.actions.GenreDetailActions
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.base.BasePresenter
import tech.arifandi.movielistapp.ui.genre_detail.converters.AppStateToGenreDetailViewModelConverter

internal class GenreDetailPresenter(
    private val genreDetailContract: GenreDetailContract,
    private val store: ThreadSafeStore,
    stateFlowable: Flowable<AppState>,
    converter: AppStateToGenreDetailViewModelConverter
): BasePresenter() {

    private val compositeDisposable = CompositeDisposable()

    val model: Flowable<GenreDetailViewModel> = stateFlowable
        .distinctUntilChanged(::checkViewModel)
        .map(converter::convert)

    private fun checkViewModel(oldState: AppState, newState: AppState): Boolean {
        return oldState.genreDetailState.currentState == newState.genreDetailState.currentState
                && oldState.genreDetailState.moviesResult == newState.genreDetailState.moviesResult
    }

    fun startFetchingGenres(genreId: Int) = store.dispatch(GenreDetailActions.StartFetching(genreId))

    fun loadNextPage() = store.dispatch(GenreDetailActions.LoadNextPage)

    fun onMovieClicked(movie: Movie) = genreDetailContract.openMovieDetailPage(movie.id)

    fun destroy() {
        compositeDisposable.clear()
        store.dispatch(GenreDetailActions.ResetFetch)
    }

}