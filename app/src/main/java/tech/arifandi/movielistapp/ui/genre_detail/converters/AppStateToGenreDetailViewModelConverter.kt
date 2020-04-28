package tech.arifandi.movielistapp.ui.genre_detail.converters

import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.genre_detail.GenreDetailViewModel
import tech.arifandi.movielistapp.ui.genre_detail.GenreDetailViewState
import tech.arifandi.movielistapp.utils.Converter

internal class AppStateToGenreDetailViewModelConverter : Converter<AppState, GenreDetailViewModel> {

    override fun convert(input: AppState): GenreDetailViewModel {
        val state = input.genreDetailState
        val viewState = when (state.currentState) {
            GeneralPageState.Idle -> GenreDetailViewState.Idle
            GeneralPageState.Requesting -> GenreDetailViewState.Requesting
            is GeneralPageState.Failed -> GenreDetailViewState.Failed(state.currentState.cause)
            GeneralPageState.Succeed -> GenreDetailViewState.Succeed
        }
        val results = state.moviesResult
        return GenreDetailViewModel(
            viewState = viewState,
            results = results
        )
    }

}