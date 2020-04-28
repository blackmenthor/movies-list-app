package tech.arifandi.movielistapp.ui.genre_detail

import tech.arifandi.movielistapp.ui.base.BaseContract

internal interface GenreDetailContract: BaseContract {

    fun openMovieDetailPage(movieId: Int)

}