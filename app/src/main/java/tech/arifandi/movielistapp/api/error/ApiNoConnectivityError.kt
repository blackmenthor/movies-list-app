package tech.arifandi.movielistapp.api.error

import tech.arifandi.movielistapp.R

internal class ApiNoConnectivityError: ApiError(421,
    msgResource = R.string.api_connectivity_error)