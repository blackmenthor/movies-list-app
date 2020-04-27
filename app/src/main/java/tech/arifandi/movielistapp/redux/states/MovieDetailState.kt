package tech.arifandi.movielistapp.redux.states

import tech.arifandi.movielistapp.models.MovieDetail
import tech.arifandi.movielistapp.models.MovieReview
import tech.arifandi.movielistapp.models.MovieVideo

internal sealed class CurrentMovieDetailPageState {
    object Idle: CurrentMovieDetailPageState()
    object Requesting: CurrentMovieDetailPageState()
    data class Failed(val cause: Throwable): CurrentMovieDetailPageState()
    object Succeed: CurrentMovieDetailPageState()
}

internal data class MovieDetailState(
    val currentState: CurrentMovieDetailPageState = CurrentMovieDetailPageState.Idle,
    val movie: MovieDetail? = null,
    val reviews: List<MovieReview>? = listOf(),
    val videos: List<MovieVideo>? = listOf(),
    val currentReviewPage: Int = 1
)