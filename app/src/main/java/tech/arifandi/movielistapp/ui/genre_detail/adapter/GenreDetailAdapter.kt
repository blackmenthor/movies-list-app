package tech.arifandi.movielistapp.ui.genre_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.models.Movie
import tech.arifandi.movielistapp.ui.base.adapters.EndlessRVAdapter
import tech.arifandi.movielistapp.ui.base.adapters.EndlessRVType

internal class GenreDetailAdapter: EndlessRVAdapter<Movie>(R.layout.item_movie_view) {

    var onMovieClicked: (Movie) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreDetailViewHolder {
        val layout = if (viewType == EndlessRVType.REGULAR.hashCode()) {
            R.layout.item_movie_view
        } else {
            R.layout.item_view_loading
        }
        return GenreDetailViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    layout,
                    parent,
                    false
                ),
            onMovieClicked
        )
    }

}