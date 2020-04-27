package tech.arifandi.movielistapp.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
internal data class MovieDetail(
    @Json(name = "id") val id: Int,
    @Json(name = "popularity") val popularity: Double?,
    @Json(name = "vote_count") val voteCount: Int?,
    @Json(name = "video") val video: Boolean?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "adult") val forAdult: Boolean?,
    @Json(name = "original_language") val language: String?,
    @Json(name = "original_title") val originalTitle: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    @Json(name = "title") val title: String?,
    @Json(name = "vote_average") val voteAvg: Double?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "budget") val budget: Int?,
    @Json(name = "homepage") val homepage: String?,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "revenue") val revenue: Int?,
    @Json(name = "runtime") val runtime: Int?,
    @Json(name = "status") val status: String?,
    @Json(name = "tagline") val tagline: String?,
    @Json(name = "genres") val genres: List<Genre>,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountry>,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>
    )