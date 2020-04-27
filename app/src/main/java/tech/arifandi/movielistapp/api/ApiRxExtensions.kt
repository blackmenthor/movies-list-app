package tech.arifandi.movielistapp.api

import tech.arifandi.movielistapp.api.error.ApiError
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.HttpException
import tech.arifandi.movielistapp.api.error.ApiGenericError
import tech.arifandi.movielistapp.api.error.ApiNoConnectivityError
import tech.arifandi.movielistapp.logging.Logger
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private fun parseError(error: Throwable): ApiError {
    Logger.Instance.logError(error)
    var parsedError: ApiError = ApiGenericError()
    if (error is UnknownHostException || error is SocketTimeoutException || error is ConnectException) {
        parsedError = ApiNoConnectivityError()
    } else if (error is HttpException) {
        parsedError = ApiError(error.code(), msg = error.message())
    }
    return parsedError
}

internal fun Completable.mapApiErrors(): Completable {
    return onErrorResumeNext { error: Throwable ->
        if (error is ApiError) // Error is already ApiError. Just use it:
            return@onErrorResumeNext Completable.error(error)

        val parsedError = parseError(error)
        Completable.error(parsedError)
    }
}

internal fun <T> Single<T>.mapApiErrors(): Single<T> {
    return onErrorResumeNext { error: Throwable ->
        if (error is ApiError) // Error is already ApiError. Just use it:
            return@onErrorResumeNext Single.error<T>(error)

        val parsedError = parseError(error)
        Single.error(parsedError)
    }
}