package tech.arifandi.movielistapp.redux.actions

import org.rekotlin.Action
import tech.arifandi.movielistapp.models.Genre

internal sealed class GenreActions {
    object ResetFetch : Action
    data class GotResults(val payload: List<Genre?>) : Action
    data class FetchFailed(val payload: Throwable) : Action
    object StartFetching : Action
}