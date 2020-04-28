package tech.arifandi.movielistapp.redux

import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.models.Genre
import tech.arifandi.movielistapp.redux.actions.GenreActions
import tech.arifandi.movielistapp.redux.di.GenreActionsModule
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.redux.states.GenresState

class GenresActionsTest {


    @Test
    fun testResetFetchAction() {
        val mockGenre = mockk<Genre>(relaxed = true)
        var testState = AppState.create()
        testState = testState.copy(
            genresState = GenresState(
                currentState = GeneralPageState.Succeed,
                genresResult = listOf(mockGenre)
            )
        )

        val subject = GenreActionsModule().providesGenreActionsFactory()

        val out = subject.reduce(GenreActions.ResetFetch, testState)

        Assert.assertTrue(out.genresState.currentState == GeneralPageState.Idle)
        Assert.assertTrue(out.genresState.genresResult.isEmpty())
    }

    @Test
    fun testFetchStarts() {
        val mockGenre = mockk<Genre>(relaxed = true)
        var testState = AppState.create()
        testState = testState.copy(
            genresState = GenresState(
                currentState = GeneralPageState.Idle,
                genresResult = listOf(mockGenre)
            )
        )

        val subject = GenreActionsModule().providesGenreActionsFactory()

        val out = subject.reduce(GenreActions.StartFetching, testState)

        Assert.assertTrue(out.genresState.currentState == GeneralPageState.Requesting)
        Assert.assertTrue(out.genresState.genresResult.isEmpty())
    }

    @Test
    fun testGotFetchResult() {
        val mockGenre = mockk<Genre>(relaxed = true)
        var testState = AppState.create()
        val genreList = listOf(mockGenre)
        testState = testState.copy(
            genresState = GenresState(
                currentState = GeneralPageState.Requesting,
                genresResult = listOf()
            )
        )

        val subject = GenreActionsModule().providesGenreActionsFactory()

        val out = subject.reduce(GenreActions.GotResults(genreList), testState)

        Assert.assertTrue(out.genresState.currentState == GeneralPageState.Succeed)
        Assert.assertTrue(out.genresState.genresResult.size == 1)
        Assert.assertTrue(out.genresState.genresResult.contains(mockGenre))
    }

    @Test
    fun testFailed() {
        val mockThrowable = mockk<Throwable>(relaxed = true)
        val mockGenre = mockk<Genre>(relaxed = true)
        var testState = AppState.create()
        testState = testState.copy(
            genresState = GenresState(
                currentState = GeneralPageState.Succeed,
                genresResult = listOf(mockGenre)
            )
        )

        val subject = GenreActionsModule().providesGenreActionsFactory()

        val out = subject.reduce(GenreActions.FetchFailed(mockThrowable), testState)

        if (out.genresState.currentState is GeneralPageState.Failed)
            assert(out.genresState.currentState.cause == mockThrowable)
    }

}