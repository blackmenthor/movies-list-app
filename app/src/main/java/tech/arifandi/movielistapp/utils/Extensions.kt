package tech.arifandi.movielistapp.utils

import android.view.View
import tech.arifandi.movielistapp.api.ApiConstants

fun Boolean.asVisibility() = if (this) View.VISIBLE else View.GONE

fun String.convertToImagePath() = ApiConstants.URL.imageUrl+this