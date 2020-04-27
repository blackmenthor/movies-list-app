package tech.arifandi.movielistapp.api

internal class ApiConstants {

    object Keys {
        const val searchQuery = "q"
        const val itemsPerPageQuery = "per_page"
        const val page = "page"
        const val withGenre = "with_genres"
        const val apiKey = "api_key"
        const val id = "id"
    }

    object Headers {
        const val authorization = "Authorization"
    }

    object Default {
        const val itemsPerPage = 25
    }

    object URL {
        const val imageUrl = "https://image.tmdb.org/t/p/w500/"
    }
}