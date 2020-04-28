package tech.arifandi.movielistapp.ui.movie_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.models.MovieReview
import tech.arifandi.movielistapp.ui.base.adapters.EndlessRVAdapter
import tech.arifandi.movielistapp.ui.base.adapters.EndlessRVType

internal class ReviewAdapter: EndlessRVAdapter<MovieReview>(R.layout.item_movie_view) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val layout = if (viewType == EndlessRVType.REGULAR.hashCode()) {
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

}