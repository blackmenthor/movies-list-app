package tech.arifandi.movielistapp.ui.base

internal interface BaseGraph<V: BaseActivity> {
    fun inject(view: V)
}