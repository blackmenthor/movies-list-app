package tech.arifandi.movielistapp.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
internal data class ProductionCountry(
    @Json(name = "iso_3166_1") val iso: String?,
    @Json(name = "name") val name: String?
)