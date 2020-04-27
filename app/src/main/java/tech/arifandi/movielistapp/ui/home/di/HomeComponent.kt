package tech.arifandi.movielistapp.ui.home.di

import dagger.Component
import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.ui.home.HomeActivity

@HomeScope
@Component(modules = [HomeModule::class], dependencies = [SampleAppComponent::class])
internal interface HomeComponent {
    fun injectTo(activity: HomeActivity)
}