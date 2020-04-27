package tech.arifandi.movielistapp.ui.movie_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.*
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.models.MovieReview

private enum class ReviewType {
    REGULAR,
    LOADING
}

internal class ReviewAdapter: Adapter<ReviewViewHolder>() {

    var onLoadingViewDrawn: () -> Unit = {}

    init {
        setHasStableIds(true)
    }

    internal var results: List<MovieReview?>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val layout = if (viewType == ReviewType.REGULAR.hashCode()) {
            R.layout.item_review_view
        } else {
            R.layout.item_view_loading
        }
        return ReviewViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    layout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return results?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        val results = results ?: return ReviewType.REGULAR.hashCode()
        val result = results[position]
        return if (result == null) {
            ReviewType.LOADING.hashCode()
        } else {
            ReviewType.REGULAR.hashCode()
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

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val results = results ?: return
        val result = results[position]
        if (result == null) { // we don't have to bind anything for the loading view
            onLoadingViewDrawn()
            return
        }
        holder.bind(result)
    }

}