package tech.arifandi.movielistapp.ui.home.converters

import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.redux.states.CurrentGenrePageState
import tech.arifandi.movielistapp.ui.home.HomeViewModel
import tech.arifandi.movielistapp.ui.home.HomeViewState
import tech.arifandi.movielistapp.utils.Converter

internal class AppStateToHomeViewModelConverter : Converter<AppState, HomeViewModel> {

    override fun convert(input: AppState): HomeViewModel {
        val genresState = input.genresState
        val viewState = when (genresState.currentState) {
            CurrentGenrePageState.Idle -> HomeViewState.Idle
            CurrentGenrePageState.Requesting -> HomeViewState.Requesting
            is CurrentGenrePageState.Failed -> HomeViewState.Failed(genresState.currentState.cause)
            CurrentGenrePageState.Succeed -> HomeViewState.Succeed
        }
        val results = genresState.genresResult
        return HomeViewModel(
            viewState = viewState,
            results = results
        )
    }

}