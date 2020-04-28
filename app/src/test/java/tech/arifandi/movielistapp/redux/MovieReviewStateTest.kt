package tech.arifandi.movielistapp.redux

import org.junit.Test
import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.states.MovieReviewListState

class MovieReviewStateTest {

    @Test
    fun testInitialState() {
        val state = MovieReviewListState()

        assert(state.currentState == GeneralPageState.Idle)
        assert(state.currentMovieId == null)
        assert(state.reviews.isNullOrEmpty())
        assert(state.currentPage == 1)
    }

}