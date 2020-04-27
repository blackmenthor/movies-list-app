package tech.arifandi.movielistapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.*
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.models.Genre

internal class GenresAdapter: Adapter<GenreViewHolder>() {

    var onGenreClicked: (Genre) -> Unit = {}

    init {
        setHasStableIds(true)
    }

    internal var results: List<Genre?>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
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

    override fun getItemCount(): Int {
        return results?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        val results = results ?: return 0L
        val result = results[position]
        return result
            ?.id
            .hashCode()
            .toLong()
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val results = results ?: return
        val result = results[position]
        holder.bind(result)
    }

}