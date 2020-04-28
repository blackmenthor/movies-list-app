package tech.arifandi.movielistapp.ui.home

import tech.arifandi.movielistapp.models.Genre
import tech.arifandi.movielistapp.ui.base.BaseContract

internal interface HomeContract: BaseContract {

    fun goToGenreDetailActivity(genre: Genre)

}