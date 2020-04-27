package tech.arifandi.movielistapp.redux.actions

import org.rekotlin.Action
import tech.arifandi.movielistapp.models.Movie

internal sealed class GenreDetailActions {
    object ResetFetch : Action
    data class GotResults(val payload: List<Movie>, val loadMore: Boolean = true) : Action
    data class FetchFailed(val payload: Throwable) : Action
    data class StartFetching(val payload: Int) : Action
    object LoadNextPage : Action
    object LoadNextPageError : Action
}