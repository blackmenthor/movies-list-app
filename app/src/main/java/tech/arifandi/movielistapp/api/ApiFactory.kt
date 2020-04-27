package tech.arifandi.movielistapp.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import tech.arifandi.movielistapp.api.client.MoviesApiClient
import tech.arifandi.movielistapp.api.datasource.MoviesDataSource
import tech.arifandi.movielistapp.logging.Logger
import tech.arifandi.movielistapp.utils.BuildConfigWrapper
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

internal class ApiFactory(private val logger: Logger) {

    fun create(
        baseHeadersInterceptor: BaseHeadersInterceptor,
        buildConfigWrapper: BuildConfigWrapper
    ): Api {
        val httpClient = OkHttpClient.Builder()
            .readTimeout(GLOBAL_READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request()
                val url = URLDecoder.decode(request.url().toString(), "UTF-8")
                val method = request.method().toUpperCase()
                logger.logInfo("Performing request: $method $url")
                chain.proceed(chain.request())
            }
            .addInterceptor(baseHeadersInterceptor)

        val moshi = Moshi.Builder()
            .add(CustomDateAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(buildConfigWrapper.getApiDomain())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val genreDataSource = MoviesDataSource(
            retrofit.create(MoviesApiClient::class.java)
        )

        return Api(
            genreDataSource
        )
    }

    private companion object {
        const val GLOBAL_READ_TIMEOUT = 30L
    }
}