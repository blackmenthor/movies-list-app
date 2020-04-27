package tech.arifandi.movielistapp.ui.movie_detail.di

import dagger.Module
import dagger.Provides
import io.reactivex.Flowable
import tech.arifandi.movielistapp.redux.ThreadSafeStore
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.movie_detail.MovieDetailPresenter
import tech.arifandi.movielistapp.ui.movie_detail.MovieDetailView
import tech.arifandi.movielistapp.ui.movie_detail.adapter.ReviewAdapter
import tech.arifandi.movielistapp.ui.movie_detail.converters.AppStateToMovieDetailViewModelConverter

@Module
internal class MovieDetailModule(private val view: MovieDetailView) {

    @Provides
    @MovieDetailScope
    fun providesAdapter(): ReviewAdapter {
        return ReviewAdapter()
    }

    @Provides
    @MovieDetailScope
    fun providesConverter(): AppStateToMovieDetailViewModelConverter {
        return AppStateToMovieDetailViewModelConverter()
    }

    @Provides
    @MovieDetailScope
    fun providesPresenter(
        store: ThreadSafeStore,
        stateFlowable: Flowable<AppState>,
        converter: AppStateToMovieDetailViewModelConverter
    ): MovieDetailPresenter {
        return MovieDetailPresenter(view, store, stateFlowable, converter)
    }

}