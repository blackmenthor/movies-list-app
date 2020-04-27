package tech.arifandi.movielistapp.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.arifandi.movielistapp.models.Movie

@JsonClass(generateAdapter = true)
internal data class GenreDetailResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "results")
    val movies: List<Movie>
)