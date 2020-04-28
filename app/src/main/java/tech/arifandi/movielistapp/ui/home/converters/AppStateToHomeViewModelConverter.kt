package tech.arifandi.movielistapp.ui.home.converters

import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.home.HomeViewModel
import tech.arifandi.movielistapp.ui.home.HomeViewState
import tech.arifandi.movielistapp.utils.Converter

internal class AppStateToHomeViewModelConverter : Converter<AppState, HomeViewModel> {

    override fun convert(input: AppState): HomeViewModel {
        val genresState = input.genresState
        val viewState = when (genresState.currentState) {
            GeneralPageState.Idle -> HomeViewState.Idle
            GeneralPageState.Requesting -> HomeViewState.Requesting
            is GeneralPageState.Failed -> HomeViewState.Failed(genresState.currentState.cause)
            GeneralPageState.Succeed -> HomeViewState.Succeed
        }
        val results = genresState.genresResult
        return HomeViewModel(
            viewState = viewState,
            results = results
        )
    }

}