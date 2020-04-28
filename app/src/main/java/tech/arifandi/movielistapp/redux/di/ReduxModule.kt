package tech.arifandi.movielistapp.redux.di

import dagger.Module
import dagger.Provides
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import org.rekotlin.Action
import org.rekotlin.Reducer
import org.rekotlin.StoreSubscriber
import tech.arifandi.movielistapp.redux.ThreadSafeStore
import tech.arifandi.movielistapp.redux.states.AppState
import javax.inject.Named
import javax.inject.Singleton

internal typealias NonNullReducer = (action: Action, state: AppState) -> AppState

@Module(
    includes = [
        GenreActionsModule::class,
        GenreDetailActionsModule::class,
        MovieDetailActionsModule::class,
        MovieReviewListActionsModule::class,
        MiddlewaresModule::class
    ]
)
internal class ReduxModule {

    @Provides
    @Singleton
    fun providesAppStore(
        @Named(GenreActionsModule.FACTORY_NAME) genreActionsFactory: ActionsFactory,
        @Named(GenreDetailActionsModule.FACTORY_NAME) genreDetailActionsFactory: ActionsFactory,
        @Named(MovieDetailActionsModule.FACTORY_NAME) movieDetailActionsFactory: ActionsFactory,
        @Named(MovieReviewListActionsModule.FACTORY_NAME) movieReviewListActionsFactory: ActionsFactory,
        middlewareFactory: MiddlewareFactory
    ): ThreadSafeStore {
        return ThreadSafeStore(
            reducer = combineReducers(
                listOf(
                    genreActionsFactory::reduce,
                    genreDetailActionsFactory::reduce,
                    movieDetailActionsFactory::reduce,
                    movieReviewListActionsFactory::reduce
                )
            ),
            state = AppState.create(),
            middleware = middlewareFactory.provide()
        )
    }

    @Provides
    @Singleton
    fun providesStatePublisher(store: ThreadSafeStore): Subject<AppState> {
        val publisher = BehaviorSubject.create<AppState>()

        store.subscribe(object : StoreSubscriber<AppState> {
            override fun newState(state: AppState) {
                publisher.onNext(state)
            }
        })

        return publisher
    }

    @Provides
    fun providesStateFlowable(publisher: Subject<AppState>): Flowable<AppState> {
        return publisher.toFlowable(BackpressureStrategy.BUFFER)
    }

    private fun combineReducers(reducers: List<NonNullReducer>): Reducer<AppState> {
        fun combinedReducer(action: Action, state: AppState?): AppState {
            var newState: AppState = state ?: AppState.create()

            // Runs all reducers one by one:
            for (reducer in reducers) {
                val intermediaryState = reducer(action, newState)
                newState = intermediaryState as? AppState ?: newState
            }
            return newState
        }

        return ::combinedReducer
    }
}