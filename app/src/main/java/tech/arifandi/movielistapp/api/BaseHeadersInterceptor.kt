package tech.arifandi.movielistapp.api

import okhttp3.Interceptor
import okhttp3.Response
import tech.arifandi.movielistapp.Config

internal class BaseHeadersInterceptor(
    private val configuration: Config
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val newUrl = original
            .url()
            .newBuilder()
            .addQueryParameter(ApiConstants.Keys.apiKey, configuration.apiKey)
            .build()

        val requestBuilder = original.newBuilder()
            .url(newUrl)

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}