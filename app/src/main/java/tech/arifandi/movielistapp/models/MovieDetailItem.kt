package tech.arifandi.movielistapp.models

internal data class MovieDetailItem(
    val movieDetail: MovieDetail,
    val reviews: List<MovieReview>,
    val loadMore: Boolean,
    val videos: List<MovieVideo>? = listOf()
)