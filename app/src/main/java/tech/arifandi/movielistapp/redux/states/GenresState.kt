package tech.arifandi.movielistapp.redux.states

import tech.arifandi.movielistapp.models.Genre

internal sealed class CurrentGenrePageState {
    object Idle: CurrentGenrePageState()
    object Requesting: CurrentGenrePageState()
    data class Failed(val cause: Throwable): CurrentGenrePageState()
    object Succeed: CurrentGenrePageState()
}

internal data class GenresState(
    val currentState: CurrentGenrePageState = CurrentGenrePageState.Idle,
    val genresResult: List<Genre?> = listOf()
)