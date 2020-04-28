package tech.arifandi.movielistapp.redux.actions

import org.rekotlin.Action
import tech.arifandi.movielistapp.models.MovieDetailItem

internal sealed class MovieDetailActions {
    object ResetState : Action
    data class GotResult(val payload: MovieDetailItem) : Action
    data class FetchFailed(val payload: Throwable) : Action
    data class StartFetching(val payload: Int) : Action
}