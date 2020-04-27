package tech.arifandi.movielistapp.api.datasource

import io.reactivex.Single
import tech.arifandi.movielistapp.api.ApiSource
import tech.arifandi.movielistapp.api.client.MoviesApiClient
import tech.arifandi.movielistapp.api.response.GenreDetailResponse
import tech.arifandi.movielistapp.api.response.GenreResponse
import tech.arifandi.movielistapp.api.response.MovieReviewsResponse
import tech.arifandi.movielistapp.api.response.MovieVideosResponse
import tech.arifandi.movielistapp.models.MovieDetail

internal class MoviesDataSource(
    private val apiClient: MoviesApiClient
) : DataSource() {

    /**
     * Get all genres
     */
    fun getAllGenres(
        source: ApiSource = ApiSource.REMOTE_ONLY
    ): Single<GenreResponse> {
        return executeRequest(
            when (source) {
                ApiSource.REMOTE_ONLY -> apiClient.getAllGenres()
            }
        )
    }

    /**
     * Get all movies by a certain genre
     */
    fun getMoviesByGenre(
        genreId: Int,
        page: Int,
        source: ApiSource = ApiSource.REMOTE_ONLY
    ): Single<GenreDetailResponse> {
        return executeRequest(
            when (source) {
                ApiSource.REMOTE_ONLY -> apiClient.getMoviesByGenre(genreId, page)
            }
        )
    }

    /**
     * Get movie detail by ID
     */
    fun getMovieDetail(
        movieId: Int,
        source: ApiSource = ApiSource.REMOTE_ONLY
    ): Single<MovieDetail> {
        return executeRequest(
            when (source) {
                ApiSource.REMOTE_ONLY -> apiClient.getMovieDetail(movieId)
            }
        )
    }

    /**
     * Get movie reviews by Movie ID
     */
    fun getMovieReviews(
        movieId: Int,
        page: Int,
        source: ApiSource = ApiSource.REMOTE_ONLY
    ): Single<MovieReviewsResponse> {
        return executeRequest(
            when (source) {
                ApiSource.REMOTE_ONLY -> apiClient.getMovieReviews(movieId, page)
            }
        )
    }

    /**
     * Get movie videos/trailers by Movie ID
     */
    fun getMovieVideos(
        movieId: Int,
        source: ApiSource = ApiSource.REMOTE_ONLY
    ): Single<MovieVideosResponse> {
        return executeRequest(
            when (source) {
                ApiSource.REMOTE_ONLY -> apiClient.getMovieVideos(movieId)
            }
        )
    }
}