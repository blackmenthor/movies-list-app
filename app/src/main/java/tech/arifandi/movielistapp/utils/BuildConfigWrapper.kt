package tech.arifandi.movielistapp.utils

import tech.arifandi.movielistapp.BuildConfig

class BuildConfigWrapper {
    fun isDebug() = BuildConfig.DEBUG
    fun getApiKey() = BuildConfig.API_KEY
    fun getApiDomain() = BuildConfig.API_DOMAIN
}