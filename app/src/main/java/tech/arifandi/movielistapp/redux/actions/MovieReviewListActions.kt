package tech.arifandi.movielistapp.redux.actions

import org.rekotlin.Action
import tech.arifandi.movielistapp.models.MovieReview

internal sealed class MovieReviewListActions {
    object ResetState : Action
    data class GotResult(val payload: List<MovieReview>, val loadMore: Boolean = false) : Action
    data class FetchFailed(val payload: Throwable) : Action
    data class StartFetching(val payload: Int) : Action

    object LoadNextPage : Action
    object LoadNextPageError : Action
}