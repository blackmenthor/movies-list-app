package tech.arifandi.movielistapp.redux.di

import dagger.Module
import dagger.Provides
import tech.arifandi.movielistapp.redux.actions.GenreActions
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.redux.states.CurrentGenrePageState
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class GenreActionsModule {

    @Provides
    @Singleton
    @Named(FACTORY_NAME)
    fun providesGenreActionsFactory(): ActionsFactory {
        return ActionsFactory()
            .register(GenreActions.ResetFetch::class, ::resetSearchStateReducer)
            .register(GenreActions.StartFetching::class, ::searchStartsStateReducer)
            .register(GenreActions.GotResults::class, ::gotResultsStateReducer)
            .register(GenreActions.FetchFailed::class, ::searchFailedStateReducer)
    }

    companion object {
        const val FACTORY_NAME = "genreActionsFactory"

        private fun resetSearchStateReducer(action: GenreActions.ResetFetch, state: AppState):
                AppState {

            return state.copy(
                genresState = state.genresState.copy(
                    currentState = CurrentGenrePageState.Idle,
                    genresResult = listOf()
                )
            )
        }

        private fun searchStartsStateReducer(action: GenreActions.StartFetching, state: AppState):
                AppState {

            return state.copy(
                genresState = state.genresState.copy(
                    currentState = CurrentGenrePageState.Requesting,
                    genresResult = listOf()
                )
            )
        }

        private fun gotResultsStateReducer(action: GenreActions.GotResults, state: AppState):
                AppState {

            return state.copy(
                genresState = state.genresState.copy(
                    currentState = CurrentGenrePageState.Succeed,
                    genresResult = action.payload
                )
            )
        }

        private fun searchFailedStateReducer(action: GenreActions.FetchFailed, state: AppState):
                AppState {

            return state.copy(
                genresState = state.genresState.copy(
                    currentState = CurrentGenrePageState.Failed(action.payload)
                )
            )
        }

    }
}