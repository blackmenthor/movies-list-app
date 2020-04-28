package tech.arifandi.movielistapp.ui.movie_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.models.MovieVideo
import tech.arifandi.movielistapp.ui.base.adapters.BaseRVAdapter

internal class VideoAdapter: BaseRVAdapter<MovieVideo>(R.layout.item_video_view) {

    var onOpenYoutubeClicked: (MovieVideo) -> Unit = {}

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

}