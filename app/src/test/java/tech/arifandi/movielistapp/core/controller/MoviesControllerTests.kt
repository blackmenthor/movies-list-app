package tech.arifandi.movielistapp.core.controller

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import tech.arifandi.movielistapp.api.Api
import tech.arifandi.movielistapp.api.datasource.MoviesDataSource
import tech.arifandi.movielistapp.api.response.GenreDetailResponse
import tech.arifandi.movielistapp.api.response.GenreResponse
import tech.arifandi.movielistapp.api.response.MovieReviewsResponse
import tech.arifandi.movielistapp.api.response.MovieVideosResponse
import tech.arifandi.movielistapp.logging.Logger
import tech.arifandi.movielistapp.models.Movie
import tech.arifandi.movielistapp.models.MovieDetail
import tech.arifandi.movielistapp.testutils.TestSchedulerProvider
import tech.arifandi.movielistapp.utils.SchedulerProvider

internal class MoviesControllerTests {

    lateinit var subject: MoviesController

    @MockK
    lateinit var mockApi: Api

    @MockK
    lateinit var mockDataSource: MoviesDataSource

    @MockK
    lateinit var mockLogger: Logger

    lateinit var testSchedulerProvider: SchedulerProvider

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        testSchedulerProvider = TestSchedulerProvider()

        every { mockApi.moviesDataSource } returns mockDataSource
        subject = MoviesController(
            api = mockApi,
            logger = mockLogger,
            schedulerProvider = testSchedulerProvider
        )
    }

    @Test
    fun testGetAllGenre_Success() {
        val mockResponse = mockk<GenreResponse>(relaxed = true)
        every { mockResponse.genres } returns listOf()
        every { mockDataSource.getAllGenres() } returns Single.just(mockResponse)

        subject.getAllGenre().subscribe({}, {})

        verify { mockLogger.logInfo("Got genres list: 0 items") }
    }

    @Test
    fun testGetMoviesByGenre_Success() {
        val mockResponse = mockk<GenreDetailResponse>(relaxed = true)
        val mockMovie = mockk<Movie>(relaxed = true)
        every { mockResponse.movies } returns listOf(mockMovie)
        every { mockResponse.totalResults } returns 1
        every { mockDataSource.getMoviesByGenre(1, 1) } returns Single.just(mockResponse)

        subject.getMoviesByGenre(1, 1).subscribe({}, {})

        verify { mockLogger.logInfo("Got movies for genre 1 at page 1 : 1 items") }
    }

    @Test
    fun testGetMovieDetail_Success() {
        val mockResponse = mockk<MovieDetail>(relaxed = true)
        every { mockDataSource.getMovieDetail(1) } returns Single.just(mockResponse)

        subject.getMovieDetail(1).subscribe({}, {})

        verify { mockLogger.logInfo("Got movie detail with id 1") }
    }

    @Test
    fun testGetMovieReviews_Success() {
        val mockResponse = mockk<MovieReviewsResponse>(relaxed = true)
        every { mockResponse.reviews } returns listOf()
        every { mockDataSource.getMovieReviews(1, 1) } returns Single.just(mockResponse)

        subject.getMovieReviews(1, 1).subscribe({}, {})

        verify { mockLogger.logInfo("Got movie reviews with id 1 : 0 items") }
    }

    @Test
    fun testGetMovieVideosSuccess() {
        val mockResponse = mockk<MovieVideosResponse>(relaxed = true)
        every { mockResponse.videos } returns listOf()
        every { mockDataSource.getMovieVideos(1) } returns Single.just(mockResponse)

        subject.getMovieVideos(1).subscribe({}, {})

        verify { mockLogger.logInfo("Got movie videos with id 1 : 0 items") }
    }

}