package tech.arifandi.movielistapp.redux.states

import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.models.Movie

internal data class GenreDetailState(
    val currentState: GeneralPageState = GeneralPageState.Idle,
    val currentGenreId: Int? = null,
    val currentPage: Int = 1,
    val moviesResult: List<Movie?> = listOf()
)