package tech.arifandi.movielistapp.ui.base.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRVAdapterModel {
    abstract fun getModelId(): Int
}

@Suppress("LeakingThis")
internal abstract class BaseRVAdapter<T: BaseRVAdapterModel>(
    private val itemLayout: Int
): RecyclerView.Adapter<BaseRVViewHolder<T>>() {

    init {
        setHasStableIds(true)
    }

    internal var results: List<T?>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRVViewHolder<T>

    override fun getItemCount(): Int {
        return results?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        val results = results ?: return 0L
        val result = results[position]
        val id = result?.getModelId() ?: -1 // Should not happened
        return id
            .hashCode()
            .toLong()
    }

    override fun onBindViewHolder(holder: BaseRVViewHolder<T>, position: Int) {
        val results = results ?: return
        val result = results[position] ?: return
        holder.bind(result)
    }

    override fun onViewRecycled(holder: BaseRVViewHolder<T>) {
        super.onViewRecycled(holder)
        holder.free()
    }

}