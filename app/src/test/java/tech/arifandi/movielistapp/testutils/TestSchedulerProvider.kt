package tech.arifandi.movielistapp.testutils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import tech.arifandi.movielistapp.utils.SchedulerProvider

internal open class TestSchedulerProvider(
    private val customScheduler: Scheduler = Schedulers.trampoline()
) : SchedulerProvider() {
    override fun computation() = customScheduler
    override fun newThread(): Scheduler = customScheduler
    override fun io(): Scheduler = customScheduler
    override fun mainThread(): Scheduler = customScheduler
}