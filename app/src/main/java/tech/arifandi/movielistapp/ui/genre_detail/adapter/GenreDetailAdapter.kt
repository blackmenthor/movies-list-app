package tech.arifandi.movielistapp.ui.genre_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.*
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.models.Movie
import tech.arifandi.movielistapp.ui.genre_detail.adapter.GenreDetailType

private enum class GenreDetailType {
    REGULAR,
    LOADING
}

internal class GenreDetailAdapter: Adapter<GenreDetailViewHolder>() {

    var onLoadingViewDrawn: () -> Unit = {}
    var onMovieClicked: (Movie) -> Unit = {}

    init {
        setHasStableIds(true)
    }

    internal var results: List<Movie?>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreDetailViewHolder {
        val layout = if (viewType == GenreDetailType.REGULAR.hashCode()) {
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

    override fun getItemCount(): Int {
        return results?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        val results = results ?: return GenreDetailType.REGULAR.hashCode()
        val result = results[position]
        return if (result == null) {
            GenreDetailType.LOADING.hashCode()
        } else {
            GenreDetailType.REGULAR.hashCode()
        }
    }

    override fun getItemId(position: Int): Long {
        val results = results ?: return 0L
        val result = results[position]
        val id = result?.id ?: -1 // Loading item would be null
        return id
            .hashCode()
            .toLong()
    }

    override fun onBindViewHolder(holder: GenreDetailViewHolder, position: Int) {
        val results = results ?: return
        val result = results[position]
        if (result == null) { // we don't have to bind anything for the loading view
            onLoadingViewDrawn()
            return
        }
        holder.bind(result)
    }

    override fun onViewRecycled(holder: GenreDetailViewHolder) {
        super.onViewRecycled(holder)
        holder.free()
    }

}