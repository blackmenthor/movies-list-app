package tech.arifandi.movielistapp.ui.home.adapter

import android.view.View
import kotlinx.android.synthetic.main.item_genre_view.view.*
import tech.arifandi.movielistapp.models.Genre
import tech.arifandi.movielistapp.ui.base.adapters.BaseRVViewHolder

internal class GenreViewHolder (
    view: View,
    private val onGenreClicked: (Genre) -> Unit = {}
) : BaseRVViewHolder<Genre>(view) {

    private val txtGenre = view.txtGenre

    override fun bind(item: Genre) {
        bindGenre(item.name)

        itemView.setOnClickListener { onGenreClicked(item) }
    }

    override fun free() {}

    private fun bindGenre(genre: String?) {
        genre ?: return
        txtGenre.text = genre
    }
}