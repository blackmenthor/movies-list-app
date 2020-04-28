package tech.arifandi.movielistapp.api.error

import android.content.res.Resources
import tech.arifandi.movielistapp.R

internal open class ApiError(
    private val statusCode: Int?,
    private val msg: String? = null,
    private val msgResource: Int? = null
) : Throwable() {

    fun getMessage(resources: Resources): String {
        if (msg.isNullOrEmpty() && msgResource == null) return resources.getString(R.string.generic_api_error)
        return msg ?: resources.getString(msgResource!!)
    }

    override fun toString(): String {
        return "ApiError(statusCode=$statusCode, msg=$msg, msgResource=$msgResource)"
    }

}