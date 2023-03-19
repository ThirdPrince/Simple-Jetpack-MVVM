package com.dhl.example.user.adapter

import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView

/**
 * 动态更新RecyclerView
 * 数据源变化就会更新数据
 */
class DynamicChangeCallBack<T>(private val adapter: RecyclerView.Adapter<*>) :ObservableList.OnListChangedCallback<ObservableList<T>>() {
    override fun onChanged(sender: ObservableList<T>?) {
        adapter.notifyDataSetChanged()
    }

    override fun onItemRangeChanged(
        sender: ObservableList<T>?,
        positionStart: Int,
        itemCount: Int
    ) {
        adapter.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onItemRangeInserted(
        sender: ObservableList<T>?,
        positionStart: Int,
        itemCount: Int
    ) {
        adapter.notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onItemRangeMoved(
        sender: ObservableList<T>?,
        fromPosition: Int,
        toPosition: Int,
        itemCount: Int
    ) {
        adapter.notifyItemRangeRemoved(fromPosition, itemCount)
        adapter.notifyItemRangeInserted(toPosition, itemCount)
    }

    override fun onItemRangeRemoved(
        sender: ObservableList<T>?,
        positionStart: Int,
        itemCount: Int
    ) {
        adapter.notifyItemRangeRemoved(positionStart, itemCount)
    }
}