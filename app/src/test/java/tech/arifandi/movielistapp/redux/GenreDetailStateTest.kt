package tech.arifandi.movielistapp.redux

import org.junit.Test
import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.states.GenreDetailState

class GenreDetailStateTest {

    @Test
    fun testInitialState() {
        val state = GenreDetailState()

        assert(state.currentState == GeneralPageState.Idle)
        assert(state.moviesResult.isEmpty())
        assert(state.currentGenreId == null)
        assert(state.currentPage == 1)
    }

}