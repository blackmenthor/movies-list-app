package tech.arifandi.movielistapp.redux.states

import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.models.MovieDetail
import tech.arifandi.movielistapp.models.MovieReview
import tech.arifandi.movielistapp.models.MovieVideo

internal data class MovieDetailState(
    val currentState: GeneralPageState = GeneralPageState.Idle,
    val movie: MovieDetail? = null,
    val reviews: List<MovieReview>? = listOf(),
    val moreReviewsAvailable: Boolean = false,
    val videos: List<MovieVideo>? = listOf()
)