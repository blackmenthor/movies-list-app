package tech.arifandi.movielistapp.ui.home

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import tech.arifandi.movielistapp.models.Genre
import tech.arifandi.movielistapp.redux.ThreadSafeStore
import tech.arifandi.movielistapp.redux.actions.GenreActions
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.base.BasePresenter
import tech.arifandi.movielistapp.ui.home.converters.AppStateToHomeViewModelConverter

internal class HomePresenter(
    private val homeContract: HomeContract,
    private val store: ThreadSafeStore,
    stateFlowable: Flowable<AppState>,
    converter: AppStateToHomeViewModelConverter
): BasePresenter() {

    private val compositeDisposable = CompositeDisposable()

    val model: Flowable<HomeViewModel> = stateFlowable
        .distinctUntilChanged(::checkViewModel)
        .map(converter::convert)

    private fun checkViewModel(oldState: AppState, newState: AppState): Boolean {
        return oldState.genresState.currentState == newState.genresState.currentState
                && oldState.genresState.genresResult == newState.genresState.genresResult
    }

    fun startFetchingGenres() = store.dispatch(GenreActions.StartFetching)

    fun onGenreClicked(genre: Genre) = homeContract.goToGenreDetailActivity(genre)

    fun destroy() {
        compositeDisposable.clear()
    }

}