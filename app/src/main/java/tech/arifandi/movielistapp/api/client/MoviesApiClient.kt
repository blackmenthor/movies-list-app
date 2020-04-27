package tech.arifandi.movielistapp.api.client

import io.reactivex.Single
import retrofit2.http.*
import tech.arifandi.movielistapp.api.ApiConstants
import tech.arifandi.movielistapp.api.response.GenreDetailResponse
import tech.arifandi.movielistapp.api.response.GenreResponse
import tech.arifandi.movielistapp.api.response.MovieReviewsResponse
import tech.arifandi.movielistapp.models.MovieDetail

internal interface MoviesApiClient {

    /**
     * Get all genres
     */
    @GET("genre/movie/list")
    fun getAllGenres(): Single<GenreResponse>

    /**
     * Get all movies from a certain genre
     */
    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query(ApiConstants.Keys.withGenre) genreId: Int,
        @Query(ApiConstants.Keys.page) page: Int
    ): Single<GenreDetailResponse>

    /**
     * Get movie detail by ID
     */
    @GET("movie/{id}")
    fun getMovieDetail(
        @Path(ApiConstants.Keys.id) movieId: Int
        ): Single<MovieDetail>

    /**
     * Get movie reviews by Movie ID
     */
    @GET("movie/{id}/reviews")
    fun getMovieReviews(
        @Path(ApiConstants.Keys.id) movieId: Int,
        @Query(ApiConstants.Keys.page) page: Int
    ): Single<MovieReviewsResponse>

}