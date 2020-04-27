package tech.arifandi.movielistapp.redux.di

import org.rekotlin.Action
import org.rekotlin.Middleware
import tech.arifandi.movielistapp.redux.states.AppState

internal typealias DispatchFunction<A> = (A) -> Unit
internal typealias MiddlewareFunction<A> = (AppState?, A, DispatchFunction<Action>, DispatchFunction<A>) -> Unit

internal class MiddlewareFactory {
    private val middlewares = mutableListOf<MiddlewareFunction<Action>>()

    fun register(middleware: MiddlewareFunction<Action>): MiddlewareFactory {
        middlewares.add(middleware)
        return this
    }

    private fun middlewareFunToMiddleware(f: MiddlewareFunction<Action>): Middleware<AppState> {
        return { dispatch, state ->
            { next ->
                { action ->
                    f(state(), action, dispatch, next)
                }
            }
        }
    }

    fun provide(): List<Middleware<AppState>> {
        return middlewares.map(this::middlewareFunToMiddleware)
    }
}