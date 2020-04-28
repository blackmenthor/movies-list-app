package tech.arifandi.movielistapp.ui.movie_review_list.di

import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.ui.base.BaseGraph
import tech.arifandi.movielistapp.ui.movie_review_list.MovieReviewListActivity

internal class MovieReviewListGraph: BaseGraph<MovieReviewListActivity> {

    private var builder = DaggerMovieReviewListComponent.builder()

    fun sampleAppComponent(component: SampleAppComponent): MovieReviewListGraph {
        builder.sampleAppComponent(component)
        return this
    }

    fun movieReviewListModule(module: MovieReviewListModule): MovieReviewListGraph {
        builder.movieReviewListModule(module)
        return this
    }

    override fun inject(view: MovieReviewListActivity) {
        builder.build().injectTo(view)
    }

}