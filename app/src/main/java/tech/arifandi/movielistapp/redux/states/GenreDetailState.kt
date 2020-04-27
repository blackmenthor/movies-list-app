package tech.arifandi.movielistapp.redux.states

import tech.arifandi.movielistapp.models.Movie

internal sealed class CurrentGenreDetailPageState {
    object Idle: CurrentGenreDetailPageState()
    object Requesting: CurrentGenreDetailPageState()
    data class Failed(val cause: Throwable): CurrentGenreDetailPageState()
    object Succeed: CurrentGenreDetailPageState()
}

internal data class GenreDetailState(
    val currentState: CurrentGenreDetailPageState = CurrentGenreDetailPageState.Idle,
    val currentGenreId: Int? = null,
    val currentPage: Int = 1,
    val moviesResult: List<Movie?> = listOf()
)