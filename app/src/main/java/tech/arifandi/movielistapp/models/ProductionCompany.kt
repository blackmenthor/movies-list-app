package tech.arifandi.movielistapp.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
internal data class ProductionCompany(
    @Json(name = "id") val id: Int,
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "origin_country") val originCountry: String?
)