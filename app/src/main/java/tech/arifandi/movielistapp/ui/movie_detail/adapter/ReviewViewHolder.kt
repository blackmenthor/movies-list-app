package tech.arifandi.movielistapp.ui.movie_detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_review_view.view.*
import tech.arifandi.movielistapp.models.MovieReview
import tech.arifandi.movielistapp.utils.Constants

internal class ReviewViewHolder (
    view: View
) : RecyclerView.ViewHolder(view) {

    private val txtAuthor = view.txtAuthor
    private val txtContent = view.txtContent
    private val btnReadMore = view.btnReadMore

    fun bind(item: MovieReview) {
        bindAuthor(item.author)
        bindContent(item.content)
        bindReadMore(item.content)
    }

    private fun bindReadMore(content: String?) {
        content ?: return
        btnReadMore.setOnClickListener {
            txtContent.text = content
            btnReadMore.visibility = View.GONE
        }
    }

    private fun bindAuthor(title: String?) {
        title ?: return
        txtAuthor.text = title
    }

    private fun bindContent(content: String?) {
        content ?: return
        var textContent = content
        if (textContent.length > Constants.Default.MAX_REVIEW_SNIPPET_LENGTH) {
            textContent = content.substring(0, Constants.Default.MAX_REVIEW_SNIPPET_LENGTH)+"..."
            btnReadMore.visibility = View.VISIBLE
        }
        txtContent.text = textContent
    }
}