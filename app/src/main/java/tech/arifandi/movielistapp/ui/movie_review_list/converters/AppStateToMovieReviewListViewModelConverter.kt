package tech.arifandi.movielistapp.ui.movie_review_list.converters

import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.movie_review_list.MovieReviewListViewModel
import tech.arifandi.movielistapp.ui.movie_review_list.MovieReviewListViewState
import tech.arifandi.movielistapp.utils.Converter

internal class AppStateToMovieReviewListViewModelConverter : Converter<AppState, MovieReviewListViewModel> {

    override fun convert(input: AppState): MovieReviewListViewModel {
        val state = input.movieReviewListState
        val viewState = when (state.currentState) {
            GeneralPageState.Idle -> MovieReviewListViewState.Idle
            GeneralPageState.Requesting -> MovieReviewListViewState.Requesting
            is GeneralPageState.Failed -> MovieReviewListViewState.Failed(state.currentState.cause)
            GeneralPageState.Succeed -> MovieReviewListViewState.Succeed
        }
        val reviews = state.reviews
        return MovieReviewListViewModel(
            viewState = viewState,
            reviews = reviews
        )
    }

}