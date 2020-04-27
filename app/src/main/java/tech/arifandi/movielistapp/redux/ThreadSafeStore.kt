package tech.arifandi.movielistapp.redux

import org.rekotlin.*
import tech.arifandi.movielistapp.redux.states.AppState

internal class ThreadSafeStore(
    reducer: Reducer<AppState>,
    state: AppState,
    middleware: List<Middleware<AppState>> = listOf()
) {

    private val store: Store<AppState> = Store(
        reducer = reducer,
        state = state,
        middleware = middleware,
        automaticallySkipRepeats = true
    )

    val state: AppState
        get() = store.state

    fun dispatch(action: Action) {
        synchronized(store) {
            store.dispatch(action)
        }
    }

    fun <S : StoreSubscriber<AppState>> subscribe(subscriber: S) {
        synchronized(store) {
            store.subscribe(subscriber)
        }
    }
}