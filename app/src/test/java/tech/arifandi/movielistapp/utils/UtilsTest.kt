package tech.arifandi.movielistapp.utils

import android.view.View
import junit.framework.Assert.assertEquals
import org.junit.Test
import tech.arifandi.movielistapp.api.ApiConstants

class UtilsTest {

    @Test
    fun testBooleanAsVisibility() {
        var input = true
        var output = input.asVisibility()

        assertEquals(output, View.VISIBLE)

        input = false
        output = input.asVisibility()

        assertEquals(output, View.GONE)
    }

    @Test
    fun testConvertToImagePath() {
        val input = "key"
        val output = input.convertToImagePath()
        val url = ApiConstants.URL.imageUrl

        assertEquals(output, url+input)
    }

}