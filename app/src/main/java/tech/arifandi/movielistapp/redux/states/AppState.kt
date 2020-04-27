package tech.arifandi.movielistapp.redux.states

import org.rekotlin.StateType

internal data class AppState(
    val genresState: GenresState,
    val genreDetailState: GenreDetailState,
    val movieDetailState: MovieDetailState
) : StateType {

    internal companion object {
        // Creates the default state (used for the first initialization):
        fun create(): AppState = AppState(
            genresState = GenresState(),
            genreDetailState = GenreDetailState(),
            movieDetailState = MovieDetailState()
        )
    }
}