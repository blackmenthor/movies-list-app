package tech.arifandi.movielistapp.ui.base.adapters

import android.view.ViewGroup

internal enum class EndlessRVType {
    REGULAR,
    LOADING
}

@Suppress("LeakingThis")
internal abstract class EndlessRVAdapter<T: BaseRVAdapterModel>(
    private val itemLayout: Int
): BaseRVAdapter<T>(itemLayout) {

    var onLoadingViewDrawn: () -> Unit = {}

    init {
        setHasStableIds(true)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRVViewHolder<T>

    override fun getItemViewType(position: Int): Int {
        val results = results ?: return EndlessRVType.REGULAR.hashCode()
        val result = results[position]
        return if (result == null) {
            EndlessRVType.LOADING.hashCode()
        } else {
            EndlessRVType.REGULAR.hashCode()
        }
    }

    override fun getItemId(position: Int): Long {
        val results = results ?: return 0L
        val result = results[position]
        val id = result?.getModelId() ?: -1 // Loading item would be null
        return id
            .hashCode()
            .toLong()
    }

    override fun onBindViewHolder(holder: BaseRVViewHolder<T>, position: Int) {
        val results = results ?: return
        val result = results[position]
        if (result == null) { // we don't have to bind anything for the loading view
            onLoadingViewDrawn()
            return
        }
        holder.bind(result)
    }

    override fun onViewRecycled(holder: BaseRVViewHolder<T>) {
        super.onViewRecycled(holder)
        holder.free()
    }

}