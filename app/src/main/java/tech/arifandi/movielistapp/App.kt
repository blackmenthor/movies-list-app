package tech.arifandi.movielistapp

import android.app.Application
import tech.arifandi.movielistapp.di.DaggerSampleAppComponent
import tech.arifandi.movielistapp.di.SampleAppComponent
import tech.arifandi.movielistapp.di.SampleAppModule

internal class App : Application() {

    lateinit var graph: SampleAppComponent

    override fun onCreate() {
        super.onCreate()

        init()
    }

    override fun onTerminate() {
        super.onTerminate()

        graph.providesManagerOrchestrator().stopManagers()
    }

    private fun init() {
        graph = DaggerSampleAppComponent
            .builder()
            .sampleAppModule(
                SampleAppModule(
                    this
                )
            )
            .build()
        graph.inject(this)

        graph.providesManagerOrchestrator() // to trigger manager orchestrator
    }

}