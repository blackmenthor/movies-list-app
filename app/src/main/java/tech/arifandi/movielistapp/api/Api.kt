package tech.arifandi.movielistapp.api

import tech.arifandi.movielistapp.api.datasource.MoviesDataSource

internal class Api(
    val moviesDataSource: MoviesDataSource
)