package tech.arifandi.movielistapp.redux.di

import dagger.Module
import dagger.Provides
import tech.arifandi.movielistapp.models.GeneralPageState
import tech.arifandi.movielistapp.redux.actions.GenreActions
import tech.arifandi.movielistapp.redux.states.AppState
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class GenreActionsModule {

    @Provides
    @Singleton
    @Named(FACTORY_NAME)
    fun providesGenreActionsFactory(): ActionsFactory {
        return ActionsFactory()
            .register(GenreActions.ResetFetch::class, ::resetFetchStateReducer)
            .register(GenreActions.StartFetching::class, ::startFetchingStateReducer)
            .register(GenreActions.GotResults::class, ::gotResultsStateReducer)
            .register(GenreActions.FetchFailed::class, ::fetchFailedStateReducer)
    }

    companion object {
        const val FACTORY_NAME = "genreActionsFactory"

        private fun resetFetchStateReducer(action: GenreActions.ResetFetch, state: AppState):
                AppState {

            return state.copy(
                genresState = state.genresState.copy(
                    currentState = GeneralPageState.Idle,
                    genresResult = listOf()
                )
            )
        }

        private fun startFetchingStateReducer(action: GenreActions.StartFetching, state: AppState):
                AppState {

            return state.copy(
                genresState = state.genresState.copy(
                    currentState = GeneralPageState.Requesting,
                    genresResult = listOf()
                )
            )
        }

        private fun gotResultsStateReducer(action: GenreActions.GotResults, state: AppState):
                AppState {

            return state.copy(
                genresState = state.genresState.copy(
                    currentState = GeneralPageState.Succeed,
                    genresResult = action.payload
                )
            )
        }

        private fun fetchFailedStateReducer(action: GenreActions.FetchFailed, state: AppState):
                AppState {

            return state.copy(
                genresState = state.genresState.copy(
                    currentState = GeneralPageState.Failed(action.payload)
                )
            )
        }

    }
}