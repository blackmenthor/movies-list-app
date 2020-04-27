package tech.arifandi.movielistapp.ui.genre_detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_view.view.*
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.models.Movie
import tech.arifandi.movielistapp.utils.convertToImagePath
import java.lang.Exception

internal class GenreDetailViewHolder (
    view: View,
    private val onMovieClicked: (Movie) -> Unit = {}
) : RecyclerView.ViewHolder(view) {

    private val progressBar = view.progressBar
    private val posterImg = view.imgPoster
    private val txtTitle = view.txtTitle

    fun bind(item: Movie) {
        bindTitle(item.title)
        bindAvatar(item.posterPath)

        itemView.setOnClickListener { onMovieClicked(item) }
    }

    private fun bindTitle(title: String?) {
        title ?: return
        txtTitle.text = title
    }
    private fun bindAvatar(avatarLink: String?) {
        avatarLink ?: return
        if (avatarLink.isNotBlank()) {
            hideImage()
            Picasso
                .get()
                .load(avatarLink.convertToImagePath())
                .error(R.drawable.placeholder_error)
                .into(posterImg, object: Callback {
                    override fun onSuccess()  = showImage()

                    override fun onError(e: Exception?) = showImage()

                })
        }
    }

    private fun hideImage() {
        progressBar.visibility = View.VISIBLE
        posterImg.visibility = View.GONE
    }

    private fun showImage() {
        posterImg.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun free() {
        posterImg ?: return
        Picasso
            .get()
            .cancelRequest(posterImg)
    }
}