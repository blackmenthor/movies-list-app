package tech.arifandi.movielistapp.api.error

import tech.arifandi.movielistapp.R

internal class ApiGenericError: ApiError(420, msgResource = R.string.generic_api_error)