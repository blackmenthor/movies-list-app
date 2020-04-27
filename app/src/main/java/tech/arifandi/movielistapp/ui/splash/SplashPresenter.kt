package tech.arifandi.movielistapp.ui.splash

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import tech.arifandi.movielistapp.ui.base.BasePresenter
import java.util.concurrent.TimeUnit

internal class SplashPresenter(private val splashView: SplashView): BasePresenter() {

    private val compositeDisposable = CompositeDisposable()

    fun startCounter() {
        compositeDisposable.addAll(Observable
            .timer(3, TimeUnit.SECONDS)
            .subscribe {
                onTimerFinish()
            }
        )
    }

    fun destroy() {
        compositeDisposable.clear()
    }

    private fun onTimerFinish() {
        splashView.onTimerFinish()
    }

}