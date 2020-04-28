package tech.arifandi.movielistapp.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.arifandi.movielistapp.ui.base.adapters.BaseRVAdapterModel
import java.util.*

@JsonClass(generateAdapter = true)
internal data class Movie(
    @Json(name = "id") val id: Int,
    @Json(name = "popularity") val popularity: Double?,
    @Json(name = "vote_count") val voteCount: Int?,
    @Json(name = "video") val video: Boolean,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "adult") val forAdult: Boolean,
    @Json(name = "original_language") val language: String?,
    @Json(name = "original_title") val originalTitle: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    @Json(name = "title") val title: String?,
    @Json(name = "vote_average") val voteAvg: Double?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "release_date") val releaseDate: Date?
): BaseRVAdapterModel() {
    override fun getModelId(): Int = id
}