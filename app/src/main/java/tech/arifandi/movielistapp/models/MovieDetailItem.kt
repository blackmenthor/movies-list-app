package tech.arifandi.movielistapp.models

internal data class MovieDetailItem(
    val movieDetail: MovieDetail,
    val reviews: List<MovieReview>? = listOf(),
    val moreReviewsAvailable: Boolean = false,
    val videos: List<MovieVideo>? = listOf()
)