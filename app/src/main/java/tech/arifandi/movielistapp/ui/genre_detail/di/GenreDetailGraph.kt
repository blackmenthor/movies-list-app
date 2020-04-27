package tech.arifandi.movielistapp.ui.genre_detail.di

import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.ui.base.BaseGraph
import tech.arifandi.movielistapp.ui.genre_detail.GenreDetailActivity

internal class GenreDetailGraph: BaseGraph<GenreDetailActivity> {

    private var builder = DaggerGenreDetailComponent.builder()

    fun sampleAppComponent(component: SampleAppComponent): GenreDetailGraph {
        builder.sampleAppComponent(component)
        return this
    }

    fun genreDetailModule(module: GenreDetailModule): GenreDetailGraph {
        builder.genreDetailModule(module)
        return this
    }

    override fun inject(view: GenreDetailActivity) {
        builder.build().injectTo(view)
    }

}