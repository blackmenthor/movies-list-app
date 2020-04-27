package tech.arifandi.movielistapp.ui.movie_detail.di

import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.ui.base.BaseGraph
import tech.arifandi.movielistapp.ui.movie_detail.MovieDetailActivity

internal class MovieDetailGraph: BaseGraph<MovieDetailActivity> {

    private var builder = DaggerMovieDetailComponent.builder()

    fun sampleAppComponent(component: SampleAppComponent): MovieDetailGraph {
        builder.sampleAppComponent(component)
        return this
    }

    fun movieDetailModule(module: MovieDetailModule): MovieDetailGraph {
        builder.movieDetailModule(module)
        return this
    }

    override fun inject(view: MovieDetailActivity) {
        builder.build().injectTo(view)
    }

}