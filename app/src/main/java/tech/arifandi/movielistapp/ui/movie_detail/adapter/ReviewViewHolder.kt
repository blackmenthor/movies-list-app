package tech.arifandi.movielistapp.ui.movie_detail.adapter

import android.view.View
import kotlinx.android.synthetic.main.item_review_view.view.*
import tech.arifandi.movielistapp.models.MovieReview
import tech.arifandi.movielistapp.ui.base.adapters.BaseRVViewHolder
import tech.arifandi.movielistapp.utils.Constants

internal class ReviewViewHolder (
    view: View
) : BaseRVViewHolder<MovieReview>(view) {

    private val txtAuthor = view.txtAuthor
    private val txtContent = view.txtContent
    private val btnReadMore = view.btnReadMore
    private val btnCollapse = view.btnCollapse

    override fun bind(item: MovieReview) {
        bindAuthor(item.author)
        bindContent(item.content)
        bindReadMore(item.content)
        bindCollapse(item.content)
    }

    override fun free() {}

    private fun bindReadMore(content: String?) {
        content ?: return
        btnReadMore.setOnClickListener {
            txtContent.text = content
            btnReadMore.visibility = View.GONE
            btnCollapse.visibility = View.VISIBLE
        }
    }

    private fun bindCollapse(content: String?) {
        content ?: return
        btnCollapse.setOnClickListener {
            bindContent(content)
            btnCollapse.visibility = View.GONE
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
        } else {
            btnReadMore.visibility = View.GONE
            btnCollapse.visibility = View.GONE
        }
        txtContent.text = textContent
    }
}