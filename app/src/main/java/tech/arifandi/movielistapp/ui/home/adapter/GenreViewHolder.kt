package tech.arifandi.movielistapp.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_genre_view.view.*
import tech.arifandi.movielistapp.models.Genre

internal class GenreViewHolder (
    view: View,
    private val onGenreClicked: (Genre) -> Unit = {}
) : RecyclerView.ViewHolder(view) {

    private val txtGenre = view.txtGenre

    fun bind(item: Genre?) {
        item ?: return
        bindGenre(item.name)

        itemView.setOnClickListener { onGenreClicked(item) }
    }

    private fun bindGenre(genre: String?) {
        genre ?: return
        txtGenre.text = genre
    }
}