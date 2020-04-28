package tech.arifandi.movielistapp.api.error

import android.content.res.Resources
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import tech.arifandi.movielistapp.R

class ApiErrorTests {

    @MockK
    lateinit var mockResources: Resources

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun testApiGenericError() {
        val genericError = ApiGenericError()

        genericError.getMessage(mockResources)
        verify { mockResources.getString(R.string.generic_api_error) }
    }

    @Test
    fun testApiNoConnectivityError() {
        val noConnectivityError = ApiNoConnectivityError()

        noConnectivityError.getMessage(mockResources)
        verify { mockResources.getString(R.string.api_connectivity_error) }
    }

    @Test
    fun testNullApiError() {
        val apiError = ApiError(500)

        apiError.getMessage(mockResources)
        verify { mockResources.getString(R.string.generic_api_error) }
    }

}