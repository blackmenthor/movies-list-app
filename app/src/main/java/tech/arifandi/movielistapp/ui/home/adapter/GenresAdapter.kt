package tech.arifandi.movielistapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.models.Genre
import tech.arifandi.movielistapp.ui.base.adapters.BaseRVAdapter

internal class GenresAdapter: BaseRVAdapter<Genre>(R.layout.item_genre_view) {

    var onGenreClicked: (Genre) -> Unit = {}

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layout = R.layout.item_genre_view
        return GenreViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    layout,
                    parent,
                    false
                ),
            onGenreClicked
        )
    }

}