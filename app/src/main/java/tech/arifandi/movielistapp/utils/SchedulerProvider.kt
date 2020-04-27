package tech.arifandi.movielistapp.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal open class SchedulerProvider {
    open fun computation() = Schedulers.computation()
    open fun newThread(): Scheduler = Schedulers.newThread()
    open fun io(): Scheduler = Schedulers.io()
    open fun mainThread(): Scheduler = AndroidSchedulers.mainThread()
}