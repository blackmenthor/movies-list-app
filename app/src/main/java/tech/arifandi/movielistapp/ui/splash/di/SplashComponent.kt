package tech.arifandi.movielistapp.ui.splash.di

import dagger.Component
import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.ui.splash.SplashActivity

@SplashScope
@Component(modules = [SplashModule::class], dependencies = [SampleAppComponent::class])
internal interface SplashComponent {
    fun injectTo(activity: SplashActivity)
}