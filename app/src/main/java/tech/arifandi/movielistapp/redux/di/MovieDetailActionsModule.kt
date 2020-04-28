package tech.arifandi.movielistapp.redux.di

import dagger.Module
import dagger.Provides
import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.actions.MovieDetailActions
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.redux.states.MovieDetailState
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
            .register(MovieDetailActions.GotResult::class, ::gotFirstResultStateReducer)
            .register(MovieDetailActions.FetchFailed::class, ::fetchMovieFailedStateReducer)
    }

    companion object {
        const val FACTORY_NAME = "movieDetailsActionsFactory"

        private fun resetStateStateReducer(action: MovieDetailActions.ResetState, state: AppState):
                AppState {

            return state.copy(
                movieDetailState = MovieDetailState()
            )
        }

        private fun startFetchingStateReducer(action: MovieDetailActions.StartFetching, state: AppState):
                AppState {

            return state.copy(
                movieDetailState = state.movieDetailState.copy(
                    currentState = GeneralPageState.Requesting,
                    movie = null,
                    reviews = listOf(),
                    moreReviewsAvailable = false,
                    videos = listOf()
                )
            )
        }

        private fun gotFirstResultStateReducer(action: MovieDetailActions.GotResult, state: AppState):
                AppState {

            val movie = action.payload.movieDetail
            val reviews = action.payload.reviews
            val moreReviewsAvailable = action.payload.moreReviewsAvailable
            val videos = action.payload.videos

            return state.copy(
                movieDetailState = state.movieDetailState.copy(
                    currentState = GeneralPageState.Succeed,
                    movie = movie,
                    reviews = reviews,
                    moreReviewsAvailable = moreReviewsAvailable,
                    videos = videos
                )
            )
        }

        private fun fetchMovieFailedStateReducer(action: MovieDetailActions.FetchFailed, state: AppState):
                AppState {

            return state.copy(
                movieDetailState = state.movieDetailState.copy(
                    currentState = GeneralPageState.Failed(action.payload)
                )
            )
        }

    }
}