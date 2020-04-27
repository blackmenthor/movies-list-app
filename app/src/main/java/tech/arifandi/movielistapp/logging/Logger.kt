package tech.arifandi.movielistapp.logging

import tech.arifandi.movielistapp.utils.BuildConfigWrapper
import timber.log.Timber

open class Logger {

    private val buildConfigWrapper: BuildConfigWrapper = BuildConfigWrapper()

    init {
        if (buildConfigWrapper.isDebug())
            Timber.plant(Timber.DebugTree())
    }

    fun logVerbose(message: String) = Timber.v(message)

    fun logInfo(message: String) = Timber.i(message)

    fun logError(throwable: Throwable) = Timber.e(throwable)

    fun logError(message: String) = Timber.e(message)

    fun logWarning(message: String) = Timber.w(message)

    object Instance : Logger()

}