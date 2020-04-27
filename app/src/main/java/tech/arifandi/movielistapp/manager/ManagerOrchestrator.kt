package tech.arifandi.movielistapp.manager

internal class ManagerOrchestrator(
    private val managers: List<Manager>
) {

    init {
        startManagers()
    }

    fun startManagers() {
        managers.forEach { it.start() }
    }

    fun stopManagers() {
        managers.forEach { it.stop() }
    }

}