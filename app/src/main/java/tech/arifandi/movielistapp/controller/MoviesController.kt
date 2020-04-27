package tech.arifandi.movielistapp.controller

import io.reactivex.Single
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.api.Api
import tech.arifandi.movielistapp.api.error.ApiError
import tech.arifandi.movielistapp.api.response.GenreDetailResponse
import tech.arifandi.movielistapp.api.response.MovieReviewsResponse
import tech.arifandi.movielistapp.logging.Logger
import tech.arifandi.movielistapp.models.Genre
import tech.arifandi.movielistapp.models.MovieDetail
import tech.arifandi.movielistapp.utils.SchedulerProvider

internal class MoviesController(
    private val api: Api,
    private val logger: Logger,
    private val schedulerProvider: SchedulerProvider
) {

    /**
     * Gets all movies genre list
     */
    fun getAllGenre(): Single<List<Genre>> {
        return api
            .moviesDataSource
            .getAllGenres()
            .subscribeOn(schedulerProvider.computation())
            .doOnSuccess { logger.logInfo("Got genres list: ${it.genres.size} items") }
            .doOnError { logger.logError(it) }
            .map { it.genres }
    }

    /**
     * Gets all movies from a certain genre
     */
    fun getMoviesByGenre(genreId: Int, page: Int = 1): Single<GenreDetailResponse> {
        return api
            .moviesDataSource
            .getMoviesByGenre(genreId, page)
            .map {
                // The endpoint didn't throw an error if there's no result, so we have to add this logic here.
                if (it.totalResults == 0)
                    throw ApiError(
                        statusCode = 404,
                        msgResource = R.string.not_found_error
                    )
                it
            }
            .subscribeOn(schedulerProvider.computation())
            .doOnSuccess { logger.logInfo("Got movies for genre $genreId at page $page : ${it.movies.size} items") }
            .doOnError { logger.logError(it) }
    }

    /**
     * Gets movie by ID
     */
    fun getMovieDetail(movieId: Int): Single<MovieDetail> {
        return api
            .moviesDataSource
            .getMovieDetail(movieId)
            .subscribeOn(schedulerProvider.computation())
            .doOnSuccess { logger.logInfo("Got movie detail with id $movieId") }
            .doOnError { logger.logError(it) }
    }

    /**
     * Gets movie reviews by Movie ID
     */
    fun getMovieReviews(movieId: Int, page: Int = 1): Single<MovieReviewsResponse> {
        return api
            .moviesDataSource
            .getMovieReviews(movieId, page)
            .subscribeOn(schedulerProvider.computation())
            .doOnSuccess { logger.logInfo("Got movie reviews with id $movieId : ${it.reviews.size} items") }
            .doOnError { logger.logError(it) }
    }

}