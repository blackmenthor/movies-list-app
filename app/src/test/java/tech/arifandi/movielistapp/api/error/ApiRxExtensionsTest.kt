package tech.arifandi.movielistapp.api.error

import android.content.res.Resources
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import retrofit2.HttpException
import tech.arifandi.movielistapp.api.mapApiErrors
import tech.arifandi.movielistapp.logging.Logger
import java.net.UnknownHostException

class ApiRxExtensionsTest {

    @Test
    fun testApiErrorCompletable() {
        val mockThrowable = mockk<ApiError>(relaxed = true)
        val mockLogger= mockk<Logger>(relaxed = true)
        Completable
            .error(mockThrowable)
            .mapApiErrors()
            .subscribe({}, {
                mockLogger.logError(it) //should be called as is
            })

        verify { mockLogger.logError(mockThrowable) }
    }

    @Test
    fun testUnknwonHostErrorCompletable() {
        val mockThrowable = mockk<UnknownHostException>(relaxed = true)
        val mockLogger= mockk<Logger>(relaxed = true)
        Completable
            .error(mockThrowable)
            .mapApiErrors()
            .subscribe({}, {
                mockLogger.logError(it)
            })

        val slot = slot<ApiNoConnectivityError>()
        verify { mockLogger.logError(capture(slot)) }

        assert(slot.isCaptured)
    }

    @Test
    fun testHttpErrorCompletable() {
        val mockThrowable = mockk<HttpException>(relaxed = true)
        val mockResource = mockk<Resources>(relaxed = true)

        every { mockThrowable.code() } returns 500
        every { mockThrowable.message() } returns ERROR_MSG
        val mockLogger= mockk<Logger>(relaxed = true)
        Completable
            .error(mockThrowable)
            .mapApiErrors()
            .subscribe({}, {
                mockLogger.logError(it)
            })

        val slot = slot<ApiError>()
        verify { mockLogger.logError(capture(slot)) }

        val apiError = slot.captured
        assert(apiError.getMessage(mockResource) == ERROR_MSG)
    }

    @Test
    fun testApiErrorSingle() {
        val mockThrowable = mockk<ApiError>(relaxed = true)
        val mockLogger= mockk<Logger>(relaxed = true)
        Single
            .error<ApiError>(mockThrowable)
            .mapApiErrors()
            .subscribe({}, {
                mockLogger.logError(it) //should be called as is
            })

        verify { mockLogger.logError(mockThrowable) }
    }

    @Test
    fun testUnknwonHostErrorSingle() {
        val mockThrowable = mockk<UnknownHostException>(relaxed = true)
        val mockLogger= mockk<Logger>(relaxed = true)
        Single
            .error<UnknownHostException>(mockThrowable)
            .mapApiErrors()
            .subscribe({}, {
                mockLogger.logError(it)
            })

        val slot = slot<ApiNoConnectivityError>()
        verify { mockLogger.logError(capture(slot)) }

        assert(slot.isCaptured)
    }

    @Test
    fun testHttpErrorSingle() {
        val mockThrowable = mockk<HttpException>(relaxed = true)
        val mockResource = mockk<Resources>(relaxed = true)

        every { mockThrowable.code() } returns 500
        every { mockThrowable.message() } returns ERROR_MSG
        val mockLogger= mockk<Logger>(relaxed = true)
        Single
            .error<HttpException>(mockThrowable)
            .mapApiErrors()
            .subscribe({}, {
                mockLogger.logError(it)
            })

        val slot = slot<ApiError>()
        verify { mockLogger.logError(capture(slot)) }

        val apiError = slot.captured
        assert(apiError.getMessage(mockResource) == ERROR_MSG)
    }

    companion object {
        const val ERROR_MSG = "ERROR"
    }

}