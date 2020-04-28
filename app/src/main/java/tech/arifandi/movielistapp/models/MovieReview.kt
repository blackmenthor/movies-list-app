package tech.arifandi.movielistapp.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.arifandi.movielistapp.ui.base.adapters.BaseRVAdapterModel

@JsonClass(generateAdapter = true)
internal data class MovieReview(
    @Json(name = "id") val id: String,
    @Json(name = "url") val url: String?,
    @Json(name = "content") val content: String?,
    @Json(name = "author") val author: String?
): BaseRVAdapterModel() {
    override fun getModelId(): Int = id.hashCode()
}