package tech.arifandi.movielistapp.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.arifandi.movielistapp.models.Genre

@JsonClass(generateAdapter = true)
internal data class GenreResponse(
    @Json(name = "genres")
    val genres: List<Genre>
)