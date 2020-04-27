package tech.arifandi.movielistapp.redux.di

import dagger.Module
import dagger.Provides
import tech.arifandi.movielistapp.redux.actions.GenreDetailActions
import tech.arifandi.movielistapp.redux.states.AppState
import tech.arifandi.movielistapp.redux.states.CurrentGenreDetailPageState
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class GenreDetailActionsModule {

    @Provides
    @Singleton
    @Named(FACTORY_NAME)
    fun providesGenreDetailActionsFactory(): ActionsFactory {
        return ActionsFactory()
            .register(GenreDetailActions.ResetFetch::class, ::resetSearchStateReducer)
            .register(GenreDetailActions.StartFetching::class, ::searchStartsStateReducer)
            .register(GenreDetailActions.GotResults::class, ::gotResultsStateReducer)
            .register(GenreDetailActions.LoadNextPage::class, ::loadNextPageStateReducer)
            .register(GenreDetailActions.LoadNextPageError::class, ::loadNextPageErrorStateReducer)
            .register(GenreDetailActions.FetchFailed::class, ::searchFailedStateReducer)
    }

    companion object {
        const val FACTORY_NAME = "genreDetailsActionsFactory"

        private fun resetSearchStateReducer(action: GenreDetailActions.ResetFetch, state: AppState):
                AppState {

            return state.copy(
                genreDetailState = state.genreDetailState.copy(
                    currentState = CurrentGenreDetailPageState.Idle,
                    moviesResult = listOf(),
                    currentGenreId = null,
                    currentPage = 1
                )
            )
        }

        private fun searchStartsStateReducer(action: GenreDetailActions.StartFetching, state: AppState):
                AppState {

            return state.copy(
                genreDetailState = state.genreDetailState.copy(
                    currentState = CurrentGenreDetailPageState.Requesting,
                    moviesResult = listOf(),
                    currentGenreId = action.payload,
                    currentPage = 1
                )
            )
        }

        private fun loadNextPageStateReducer(action: GenreDetailActions.LoadNextPage, state: AppState):
                AppState {

            return state.copy(
                genreDetailState = state.genreDetailState.copy(
                    currentPage = state.genreDetailState.currentPage.plus(1)
                )
            )
        }

        private fun loadNextPageErrorStateReducer(action: GenreDetailActions.LoadNextPageError, state: AppState):
                AppState {
            return state.copy(
                genreDetailState = state.genreDetailState.copy(
                    currentPage = state.genreDetailState.currentPage.minus(1) // we undo the change before
                )
            )
        }

        private fun gotResultsStateReducer(action: GenreDetailActions.GotResults, state: AppState):
                AppState {
            val mutableList = state.genreDetailState.moviesResult.toMutableList()
            if (mutableList.isNotEmpty()) mutableList.removeAt(mutableList.size-1) // removing null from the results before if any
            mutableList.addAll(action.payload)

            if (action.loadMore) mutableList.add(null) // we add one more null here for loading purposes

            return state.copy(
                genreDetailState = state.genreDetailState.copy(
                    currentState = CurrentGenreDetailPageState.Succeed,
                    moviesResult = mutableList.toList()
                )
            )
        }

        private fun searchFailedStateReducer(action: GenreDetailActions.FetchFailed, state: AppState):
                AppState {

            return state.copy(
                genreDetailState = state.genreDetailState.copy(
                    currentState = CurrentGenreDetailPageState.Failed(action.payload)
                )
            )
        }

    }
}