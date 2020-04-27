package tech.arifandi.movielistapp.ui.movie_detail.converters

import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.redux.states.CurrentMovieDetailPageState
import tech.arifandi.movielistapp.ui.movie_detail.MovieDetailViewModel
import tech.arifandi.movielistapp.ui.movie_detail.MovieDetailViewState
import tech.arifandi.movielistapp.utils.Converter

internal class AppStateToMovieDetailViewModelConverter : Converter<AppState, MovieDetailViewModel> {

    override fun convert(input: AppState): MovieDetailViewModel {
        val state = input.movieDetailState
        val viewState = when (state.currentState) {
            CurrentMovieDetailPageState.Idle -> MovieDetailViewState.Idle
            CurrentMovieDetailPageState.Requesting -> MovieDetailViewState.Requesting
            is CurrentMovieDetailPageState.Failed -> MovieDetailViewState.Failed(state.currentState.cause)
            CurrentMovieDetailPageState.Succeed -> MovieDetailViewState.Succeed
        }
        val result = state.movie
        val reviews = state.reviews
        val videos = state.videos
        return MovieDetailViewModel(
            viewState = viewState,
            result = result,
            reviews = reviews,
            videos = videos
        )
    }

}