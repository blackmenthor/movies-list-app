package tech.arifandi.movielistapp.ui.splash.di

import dagger.Module
import dagger.Provides
import tech.arifandi.movielistapp.ui.splash.SplashPresenter
import tech.arifandi.movielistapp.ui.splash.SplashView

@Module
internal class SplashModule(private val view: SplashView) {

    @Provides
    @SplashScope
    fun providesPresenter(): SplashPresenter {
        return SplashPresenter(splashView = view)
    }

}