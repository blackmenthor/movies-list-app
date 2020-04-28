package tech.arifandi.movielistapp.redux

import org.junit.Test
import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.states.GenresState

class GenreStateTest {

    @Test
    fun testInitialState() {
        val state = GenresState()

        assert(state.currentState == GeneralPageState.Idle)
        assert(state.genresResult.isEmpty())
    }

}