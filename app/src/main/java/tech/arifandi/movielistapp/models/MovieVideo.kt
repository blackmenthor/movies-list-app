package tech.arifandi.movielistapp.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.arifandi.movielistapp.ui.base.adapters.BaseRVAdapterModel

@JsonClass(generateAdapter = true)
internal data class MovieVideo(
    @Json(name = "id") val id: String,
    @Json(name = "iso_639_1") val iso639: String?,
    @Json(name = "iso_3166_1") val iso3166: String?,
    @Json(name = "key") val key: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "site") val site: String?,
    @Json(name = "size") val size: Int?,
    @Json(name = "type") val type: String?
): BaseRVAdapterModel() {
    override fun getModelId(): Int = id.hashCode()
}