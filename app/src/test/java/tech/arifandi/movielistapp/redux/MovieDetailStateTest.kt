package tech.arifandi.movielistapp.redux

import org.junit.Test
import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.states.MovieDetailState

class MovieDetailStateTest {

    @Test
    fun testInitialState() {
        val state = MovieDetailState()

        assert(state.currentState == GeneralPageState.Idle)
        assert(!state.moreReviewsAvailable)
        assert(state.reviews.isNullOrEmpty())
        assert(state.movie == null)
        assert(state.videos.isNullOrEmpty())
    }

}