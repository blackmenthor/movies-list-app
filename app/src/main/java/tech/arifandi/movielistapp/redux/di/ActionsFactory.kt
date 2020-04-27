package tech.arifandi.movielistapp.redux.di

import org.rekotlin.Action
import tech.arifandi.movielistapp.redux.states.AppState
import kotlin.reflect.KClass

internal class ActionsFactory {
    private val actions: MutableMap<KClass<Action>, Any> = mutableMapOf()

    fun <T: Action> register(action: KClass<T>, reducer: (action: T, state: AppState) -> AppState): ActionsFactory {
        actions[action as KClass<Action>] = reducer
        return this
    }

    fun reduce(action: Action, appState: AppState): AppState {
        val reducer = actions[action::class] as? (action: Action, state: AppState) -> AppState ?: return appState
        return reducer(action, appState)
    }
}