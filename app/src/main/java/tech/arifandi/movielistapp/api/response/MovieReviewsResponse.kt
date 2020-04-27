package tech.arifandi.movielistapp.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.arifandi.movielistapp.models.MovieReview

@JsonClass(generateAdapter = true)
internal data class MovieReviewsResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "results")
    val reviews: List<MovieReview>
)