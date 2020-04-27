package tech.arifandi.movielistapp.ui.home.di

import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.ui.base.BaseGraph
import tech.arifandi.movielistapp.ui.home.HomeActivity

internal class HomeGraph: BaseGraph<HomeActivity> {

    private var builder = DaggerHomeComponent.builder()

    fun sampleAppComponent(component: SampleAppComponent): HomeGraph {
        builder.sampleAppComponent(component)
        return this
    }

    fun homeModule(module: HomeModule): HomeGraph {
        builder.homeModule(module)
        return this
    }

    override fun inject(view: HomeActivity) {
        builder.build().injectTo(view)
    }

}