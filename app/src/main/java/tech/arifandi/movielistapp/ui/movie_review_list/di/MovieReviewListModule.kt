package tech.arifandi.movielistapp.ui.movie_review_list.di

import dagger.Module
import dagger.Provides
import io.reactivex.Flowable
import tech.arifandi.movielistapp.redux.ThreadSafeStore
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.ui.movie_detail.adapter.ReviewAdapter
import tech.arifandi.movielistapp.ui.movie_detail.adapter.VideoAdapter
import tech.arifandi.movielistapp.ui.movie_review_list.MovieReviewListPresenter
import tech.arifandi.movielistapp.ui.movie_review_list.MovieReviewListContract
import tech.arifandi.movielistapp.ui.movie_review_list.converters.AppStateToMovieReviewListViewModelConverter

@Module
internal class MovieReviewListModule(private val contract: MovieReviewListContract) {

    @Provides
    @MovieReviewListScope
    fun providesAdapter(): ReviewAdapter {
        return ReviewAdapter()
    }

    @Provides
    @MovieReviewListScope
    fun providesVideoAdapter(): VideoAdapter {
        return VideoAdapter()
    }

    @Provides
    @MovieReviewListScope
    fun providesConverter(): AppStateToMovieReviewListViewModelConverter {
        return AppStateToMovieReviewListViewModelConverter()
    }

    @Provides
    @MovieReviewListScope
    fun providesPresenter(
        store: ThreadSafeStore,
        stateFlowable: Flowable<AppState>,
        converter: AppStateToMovieReviewListViewModelConverter
    ): MovieReviewListPresenter {
        return MovieReviewListPresenter(contract, store, stateFlowable, converter)
    }

}