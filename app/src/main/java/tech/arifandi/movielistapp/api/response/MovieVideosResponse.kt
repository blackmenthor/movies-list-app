package tech.arifandi.movielistapp.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.arifandi.movielistapp.models.MovieVideo

@JsonClass(generateAdapter = true)
internal data class MovieVideosResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "results")
    val videos: List<MovieVideo>
)