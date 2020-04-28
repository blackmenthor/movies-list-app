package tech.arifandi.movielistapp.models

internal sealed class GeneralPageState {
    object Idle: GeneralPageState()
    object Requesting: GeneralPageState()
    data class Failed(val cause: Throwable): GeneralPageState()
    object Succeed: GeneralPageState()
}