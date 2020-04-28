package tech.arifandi.movielistapp.redux.di

import dagger.Module
import dagger.Provides
import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.actions.MovieReviewListActions
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.redux.states.MovieReviewListState
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class MovieReviewListActionsModule {

    @Provides
    @Singleton
    @Named(FACTORY_NAME)
    fun providesMovieReviewListActionsFactory(): ActionsFactory {
        return ActionsFactory()
            .register(MovieReviewListActions.ResetState::class, ::resetStateReducer)
            .register(MovieReviewListActions.StartFetching::class, ::startFetchingStateReducer)
            .register(MovieReviewListActions.GotResult::class, ::gotResultsStateReducer)
            .register(MovieReviewListActions.LoadNextPage::class, ::loadNextPageStateReducer)
            .register(MovieReviewListActions.LoadNextPageError::class, ::loadNextPageErrorStateReducer)
            .register(MovieReviewListActions.FetchFailed::class, ::fetchFailedStateReducer)
    }

    companion object {
        const val FACTORY_NAME = "movieReviewListActionsFactory"

        private fun resetStateReducer(action: MovieReviewListActions.ResetState, state: AppState):
                AppState {

            return state.copy(
                movieReviewListState = MovieReviewListState()
            )
        }

        private fun startFetchingStateReducer(action: MovieReviewListActions.StartFetching, state: AppState):
                AppState {

            return state.copy(
                movieReviewListState = state.movieReviewListState.copy(
                    currentState = GeneralPageState.Requesting,
                    reviews = listOf(),
                    currentMovieId = action.payload,
                    currentPage = 1
                )
            )
        }

        private fun loadNextPageStateReducer(action: MovieReviewListActions.LoadNextPage, state: AppState):
                AppState {

            return state.copy(
                movieReviewListState = state.movieReviewListState.copy(
                    currentPage = state.movieReviewListState.currentPage.plus(1)
                )
            )
        }

        private fun loadNextPageErrorStateReducer(action: MovieReviewListActions.LoadNextPageError, state: AppState):
                AppState {
            return state.copy(
                movieReviewListState = state.movieReviewListState.copy(
                    currentPage = state.movieReviewListState.currentPage.minus(1) // we undo the change before
                )
            )
        }

        private fun gotResultsStateReducer(action: MovieReviewListActions.GotResult, state: AppState):
                AppState {
            val mutableList = state.movieReviewListState.reviews.toMutableList()
            if (mutableList.isNotEmpty()) mutableList.removeAt(mutableList.size-1) // removing null from the results before if any
            mutableList.addAll(action.payload)

            if (action.loadMore) mutableList.add(null) // we add one more null here for loading purposes

            return state.copy(
                movieReviewListState = state.movieReviewListState.copy(
                    currentState = GeneralPageState.Succeed,
                    reviews = mutableList.toList()
                )
            )
        }

        private fun fetchFailedStateReducer(action: MovieReviewListActions.FetchFailed, state: AppState):
                AppState {

            return state.copy(
                movieReviewListState = state.movieReviewListState.copy(
                    currentState = GeneralPageState.Failed(action.payload)
                )
            )
        }

    }
}