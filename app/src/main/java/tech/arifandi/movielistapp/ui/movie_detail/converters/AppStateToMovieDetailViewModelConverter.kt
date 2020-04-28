package tech.arifandi.movielistapp.ui.movie_detail.converters

import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.movie_detail.MovieDetailViewModel
import tech.arifandi.movielistapp.ui.movie_detail.MovieDetailViewState
import tech.arifandi.movielistapp.utils.Converter

internal class AppStateToMovieDetailViewModelConverter : Converter<AppState, MovieDetailViewModel> {

    override fun convert(input: AppState): MovieDetailViewModel {
        val state = input.movieDetailState
        val viewState = when (state.currentState) {
            GeneralPageState.Idle -> MovieDetailViewState.Idle
            GeneralPageState.Requesting -> MovieDetailViewState.Requesting
            is GeneralPageState.Failed -> MovieDetailViewState.Failed(state.currentState.cause)
            GeneralPageState.Succeed -> MovieDetailViewState.Succeed
        }
        val result = state.movie
        val reviews = state.reviews
        val moreReviewsAvailable = state.moreReviewsAvailable
        val videos = state.videos
        return MovieDetailViewModel(
            viewState = viewState,
            result = result,
            reviews = reviews,
            moreReviewsAvailable = moreReviewsAvailable,
            videos = videos
        )
    }

}