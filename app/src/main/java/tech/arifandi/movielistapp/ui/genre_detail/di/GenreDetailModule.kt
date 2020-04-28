package tech.arifandi.movielistapp.ui.genre_detail.di

import dagger.Module
import dagger.Provides
import io.reactivex.Flowable
import tech.arifandi.movielistapp.redux.ThreadSafeStore
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.genre_detail.GenreDetailPresenter
import tech.arifandi.movielistapp.ui.genre_detail.GenreDetailContract
import tech.arifandi.movielistapp.ui.genre_detail.adapter.GenreDetailAdapter
import tech.arifandi.movielistapp.ui.genre_detail.converters.AppStateToGenreDetailViewModelConverter

@Module
internal class GenreDetailModule(private val contract: GenreDetailContract) {

    @Provides
    @GenreDetailScope
    fun providesAdapter(): GenreDetailAdapter {
        return GenreDetailAdapter()
    }

    @Provides
    @GenreDetailScope
    fun providesConverter(): AppStateToGenreDetailViewModelConverter {
        return AppStateToGenreDetailViewModelConverter()
    }

    @Provides
    @GenreDetailScope
    fun providesPresenter(
        store: ThreadSafeStore,
        stateFlowable: Flowable<AppState>,
        converter: AppStateToGenreDetailViewModelConverter
    ): GenreDetailPresenter {
        return GenreDetailPresenter(contract, store, stateFlowable, converter)
    }

}