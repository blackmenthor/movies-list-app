package tech.arifandi.movielistapp.di

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import dagger.Module
import dagger.Provides
import tech.arifandi.movielistapp.Config
import tech.arifandi.movielistapp.App
import tech.arifandi.movielistapp.api.Api
import tech.arifandi.movielistapp.api.ApiFactory
import tech.arifandi.movielistapp.api.BaseHeadersInterceptor
import tech.arifandi.movielistapp.controller.MoviesController
import tech.arifandi.movielistapp.logging.Logger
import tech.arifandi.movielistapp.manager.ManagerOrchestrator
import tech.arifandi.movielistapp.utils.BuildConfigWrapper
import tech.arifandi.movielistapp.utils.SchedulerProvider
import javax.inject.Singleton

@Module
internal class SampleAppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideApplication(): App = app

    @Provides
    @Singleton
    fun provideContext(): Context = app.baseContext

    @Provides
    @Singleton
    fun provideResources(): Resources = app.resources

    @Provides
    @Singleton
    fun providConfig(buildConfigWrapper: BuildConfigWrapper): Config = Config(buildConfigWrapper.getApiKey())

    @Provides
    @Singleton
    fun provideLayoutInflater(context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }

    @Provides
    @Singleton
    fun provideBuildConfigWrapper(): BuildConfigWrapper = BuildConfigWrapper()

    @Provides
    @Singleton
    fun provideLogger(): Logger = Logger.Instance

    @Provides
    @Singleton
    fun providesApiFactory(logger: Logger): ApiFactory = ApiFactory(logger)

    @Provides
    @Singleton
    fun providesBaseHeaderInterceptor(config: Config): BaseHeadersInterceptor = BaseHeadersInterceptor(config)

    @Provides
    @Singleton
    fun providesApi(
        apiFactory: ApiFactory,
        baseHeadersInterceptor: BaseHeadersInterceptor,
        buildConfigWrapper: BuildConfigWrapper
    ): Api = apiFactory.create(baseHeadersInterceptor, buildConfigWrapper)

    @Provides
    @Singleton
    fun providesSchedulerProvider(): SchedulerProvider = SchedulerProvider()

    @Provides
    @Singleton
    fun providesMoviesController(
        api: Api,
        logger: Logger,
        schedulerProvider: SchedulerProvider
    ): MoviesController = MoviesController(api, logger, schedulerProvider)

    @Provides
    @Singleton
    fun providesManagerOrchestrator(): ManagerOrchestrator
            = ManagerOrchestrator(listOf())
}