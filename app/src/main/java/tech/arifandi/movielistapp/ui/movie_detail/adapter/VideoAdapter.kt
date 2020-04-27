package tech.arifandi.movielistapp.ui.movie_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.*
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.models.MovieVideo

internal class VideoAdapter: Adapter<VideoViewHolder>() {

    var onOpenYoutubeClicked: (MovieVideo) -> Unit = {}

    init {
        setHasStableIds(true)
    }

    internal var results: List<MovieVideo>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_video_view,
                    parent,
                    false
                ),
            onOpenYoutubeClicked
        )
    }

    override fun getItemCount(): Int {
        return results?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        val results = results ?: return 0L
        val result = results[position]
        val id = result
        return id
            .hashCode()
            .toLong()
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val results = results ?: return
        val result = results[position]
        holder.bind(result)
    }

}