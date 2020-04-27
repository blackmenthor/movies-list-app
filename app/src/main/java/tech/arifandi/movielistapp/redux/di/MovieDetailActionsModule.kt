package tech.arifandi.movielistapp.redux.di

import dagger.Module
import dagger.Provides
import tech.arifandi.movielistapp.redux.actions.MovieDetailActions
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.redux.states.CurrentMovieDetailPageState
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class MovieDetailActionsModule {

    @Provides
    @Singleton
    @Named(FACTORY_NAME)
    fun providesMovieDetailActionsFactory(): ActionsFactory {
        return ActionsFactory()
            .register(MovieDetailActions.ResetState::class, ::resetStateStateReducer)
            .register(MovieDetailActions.StartFetching::class, ::startFetchingStateReducer)
            .register(MovieDetailActions.GotFirstResult::class, ::gotFirstResultStateReducer)
            .register(MovieDetailActions.FetchFailed::class, ::fetchMovieFailedStateReducer)
            .register(MovieDetailActions.LoadNextReviewPage::class, ::loadNextReviewPageStateReducer)
            .register(MovieDetailActions.LoadNextReviewPageError::class, ::loadNextReviewPageErrorStateReducer)
    }

    companion object {
        const val FACTORY_NAME = "movieDetailsActionsFactory"

        private fun resetStateStateReducer(action: MovieDetailActions.ResetState, state: AppState):
                AppState {

            return state.copy(
                movieDetailState = state.movieDetailState.copy(
                    currentState = CurrentMovieDetailPageState.Idle,
                    movie = null,
                    currentReviewPage = 1,
                    reviews = listOf()
                )
            )
        }

        private fun startFetchingStateReducer(action: MovieDetailActions.StartFetching, state: AppState):
                AppState {

            return state.copy(
                movieDetailState = state.movieDetailState.copy(
                    currentState = CurrentMovieDetailPageState.Requesting,
                    movie = null,
                    currentReviewPage = 1,
                    reviews = listOf()
                )
            )
        }

        private fun loadNextReviewPageStateReducer(action: MovieDetailActions.LoadNextReviewPage, state: AppState):
                AppState {

            return state.copy(
                genreDetailState = state.genreDetailState.copy(
                    currentPage = state.genreDetailState.currentPage.plus(1)
                )
            )
        }

        private fun loadNextReviewPageErrorStateReducer(action: MovieDetailActions.LoadNextReviewPageError, state: AppState):
                AppState {
            return state.copy(
                genreDetailState = state.genreDetailState.copy(
                    currentPage = state.genreDetailState.currentPage.minus(1) // we undo the change before
                )
            )
        }

        private fun gotFirstResultStateReducer(action: MovieDetailActions.GotFirstResult, state: AppState):
                AppState {

            val movie = action.payload.first
            val reviews = action.payload.second

            return state.copy(
                movieDetailState = state.movieDetailState.copy(
                    currentState = CurrentMovieDetailPageState.Succeed,
                    movie = movie,
                    reviews = reviews,
                    currentReviewPage = 1
                )
            )
        }

        private fun fetchMovieFailedStateReducer(action: MovieDetailActions.FetchFailed, state: AppState):
                AppState {

            return state.copy(
                movieDetailState = state.movieDetailState.copy(
                    currentState = CurrentMovieDetailPageState.Failed(action.payload)
                )
            )
        }

    }
}