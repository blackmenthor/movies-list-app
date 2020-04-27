package tech.arifandi.movielistapp.ui.splash.di

import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.ui.base.BaseGraph
import tech.arifandi.movielistapp.ui.home.HomeActivity
import tech.arifandi.movielistapp.ui.splash.SplashActivity

internal class SplashGraph: BaseGraph<SplashActivity> {

    private var builder = DaggerSplashComponent.builder()

    fun sampleAppComponent(component: SampleAppComponent): SplashGraph {
        builder.sampleAppComponent(component)
        return this
    }

    fun homeModule(module: SplashModule): SplashGraph {
        builder.splashModule(module)
        return this
    }

    override fun inject(view: SplashActivity) {
        builder.build().injectTo(view)
    }

}