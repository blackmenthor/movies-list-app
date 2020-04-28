package tech.arifandi.movielistapp.redux.states

import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.models.MovieReview

internal data class MovieReviewListState(
    val currentState: GeneralPageState = GeneralPageState.Idle,
    val currentMovieId: Int? = null,
    val reviews: List<MovieReview?> = listOf(),
    val currentPage: Int = 1
)