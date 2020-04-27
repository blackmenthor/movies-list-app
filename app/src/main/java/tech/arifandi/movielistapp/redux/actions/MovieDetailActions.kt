package tech.arifandi.movielistapp.redux.actions

import org.rekotlin.Action
import tech.arifandi.movielistapp.models.MovieDetail
import tech.arifandi.movielistapp.models.MovieReview

internal sealed class MovieDetailActions {
    object ResetState : Action
    data class GotFirstResult(val payload: Pair<MovieDetail, List<MovieReview>>, val loadMore: Boolean = true) : Action
    data class FetchFailed(val payload: Throwable) : Action
    data class StartFetching(val payload: Int) : Action

    object LoadNextReviewPage : Action
    object LoadNextReviewPageError : Action
}