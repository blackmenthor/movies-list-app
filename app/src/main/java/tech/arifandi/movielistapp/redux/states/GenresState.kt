package tech.arifandi.movielistapp.redux.states

import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.models.Genre

internal data class GenresState(
    val currentState: GeneralPageState = GeneralPageState.Idle,
    val genresResult: List<Genre?> = listOf()
)