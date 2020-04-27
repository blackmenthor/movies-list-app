package tech.arifandi.movielistapp.ui.movie_detail.di

import dagger.Component
import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.ui.movie_detail.MovieDetailActivity

@MovieDetailScope
@Component(modules = [MovieDetailModule::class], dependencies = [SampleAppComponent::class])
internal interface MovieDetailComponent {
    fun injectTo(activity: MovieDetailActivity)
}