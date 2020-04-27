package tech.arifandi.movielistapp.api.datasource

import io.reactivex.Completable
import io.reactivex.Single
import tech.arifandi.movielistapp.api.mapApiErrors

internal abstract class DataSource {

    internal fun <T> executeRequest(single: Single<T>): Single<T> {
        return single
            .mapApiErrors()
    }

    internal fun executeRequest(completable: Completable): Completable {
        return completable
            .mapApiErrors()
    }
}