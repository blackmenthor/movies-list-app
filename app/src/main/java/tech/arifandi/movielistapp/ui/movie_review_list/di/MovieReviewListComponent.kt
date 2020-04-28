package tech.arifandi.movielistapp.ui.movie_review_list.di

import dagger.Component
import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.ui.movie_review_list.MovieReviewListActivity

@MovieReviewListScope
@Component(modules = [MovieReviewListModule::class], dependencies = [SampleAppComponent::class])
internal interface MovieReviewListComponent {
    fun injectTo(activity: MovieReviewListActivity)
}