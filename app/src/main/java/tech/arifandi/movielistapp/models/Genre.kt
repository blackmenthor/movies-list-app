package tech.arifandi.movielistapp.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class Genre(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)