package tech.arifandi.movielistapp.di

import dagger.Component
import io.reactivex.Flowable
import tech.arifandi.movielistapp.App
import tech.arifandi.movielistapp.controller.MoviesController
import tech.arifandi.movielistapp.redux.ThreadSafeStore
import tech.arifandi.movielistapp.redux.di.ReduxModule
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.utils.SchedulerProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [SampleAppModule::class, ReduxModule::class])
internal interface SampleAppComponent {

    fun providesStateFlowable(): Flowable<AppState>
    fun providesMoviesController(): MoviesController
    fun providesStore(): ThreadSafeStore
    fun providesSchedulerProvider(): SchedulerProvider

    // Injectors
    fun inject(app: App)
}