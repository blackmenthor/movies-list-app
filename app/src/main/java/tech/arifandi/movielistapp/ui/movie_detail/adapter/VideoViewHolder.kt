package tech.arifandi.movielistapp.ui.movie_detail.adapter

import android.view.View
import kotlinx.android.synthetic.main.item_video_view.view.*
import tech.arifandi.movielistapp.models.MovieVideo
import tech.arifandi.movielistapp.ui.base.adapters.BaseRVViewHolder

internal class VideoViewHolder (
    view: View,
    private val onOpenYoutubeClicked: (MovieVideo) -> Unit = {}
) : BaseRVViewHolder<MovieVideo>(view) {

    private val txtName = view.txtName
    private val txtType = view.txtType
    private val btnOpenYoutube = view.btnOpenYoutube

    override fun bind(item: MovieVideo) {
        bindName(item.name)
        bindType(item.type)
        bindBtn(item)
    }

    override fun free() {}

    private fun bindBtn(item: MovieVideo) {
        btnOpenYoutube.setOnClickListener {
            onOpenYoutubeClicked(item)
        }
    }

    private fun bindName(name: String?) {
        name ?: return
        txtName.text = name
    }

    private fun bindType(type: String?) {
        type ?: return
        txtType.text = type
    }
}