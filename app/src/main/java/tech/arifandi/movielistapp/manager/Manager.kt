package tech.arifandi.movielistapp.manager

/**
 * A Manager will subscribe to state flowable and will do something based on the condition of some state.
 */
interface Manager {
    fun start()
    fun stop()
}