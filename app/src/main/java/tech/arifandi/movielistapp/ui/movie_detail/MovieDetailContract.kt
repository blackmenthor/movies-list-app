package tech.arifandi.movielistapp.ui.movie_detail

import tech.arifandi.movielistapp.ui.base.BaseContract

internal interface MovieDetailContract: BaseContract {

    fun openYoutubeVideo(videoKey: String?)
    fun goToReviewPage(movieId: Int)

}