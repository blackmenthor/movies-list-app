package tech.arifandi.movielistapp.ui.home.di

import dagger.Module
import dagger.Provides
import io.reactivex.Flowable
import tech.arifandi.movielistapp.redux.ThreadSafeStore
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.home.HomePresenter
import tech.arifandi.movielistapp.ui.home.HomeView
import tech.arifandi.movielistapp.ui.home.adapter.GenresAdapter
import tech.arifandi.movielistapp.ui.home.converters.AppStateToHomeViewModelConverter

@Module
internal class HomeModule(private val view: HomeView) {

    @Provides
    @HomeScope
    fun providesAdapter(): GenresAdapter {
        return GenresAdapter()
    }

    @Provides
    @HomeScope
    fun providesConverter(): AppStateToHomeViewModelConverter {
        return AppStateToHomeViewModelConverter()
    }

    @Provides
    @HomeScope
    fun providesPresenter(
        store: ThreadSafeStore,
        stateFlowable: Flowable<AppState>,
        converter: AppStateToHomeViewModelConverter
    ): HomePresenter {
        return HomePresenter(view, store, stateFlowable, converter)
    }

}