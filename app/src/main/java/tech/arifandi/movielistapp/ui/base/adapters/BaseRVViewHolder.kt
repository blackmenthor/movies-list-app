package tech.arifandi.movielistapp.ui.base.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRVViewHolder<T>(
    view: View
): RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T)
    abstract fun free()

}