package tech.arifandi.movielistapp.ui.genre_detail.di

import dagger.Component
import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.ui.genre_detail.GenreDetailActivity

@GenreDetailScope
@Component(modules = [GenreDetailModule::class], dependencies = [SampleAppComponent::class])
internal interface GenreDetailComponent {
    fun injectTo(activity: GenreDetailActivity)
}