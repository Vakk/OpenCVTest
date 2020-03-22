package com.vakk.myapplication.ui.list.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Base adapter for recycler view
 */
abstract class BaseRecyclerViewAdapter<TData, TViewHolder : RecyclerView.ViewHolder>
(data: List<TData> = listOf()) : RecyclerView.Adapter<TViewHolder>() {

    protected var data: MutableList<TData> = data.toMutableList()

    open val all: List<TData>
        get() = data

    open val snapshot: List<TData>
        get() = data.toMutableList()

    override fun getItemCount(): Int {
        return data.size
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    open fun getItem(position: Int): TData {
        return data[position]
    }

    open fun add(`object`: TData): Boolean {
        return data.add(`object`)
    }

    open fun add(oldPosition: Int, newPosition: Int) = data.add(newPosition, remove(oldPosition))

    open operator fun set(position: Int, `object`: TData): TData {
        return data.set(position, `object`)
    }

    open fun remove(`object`: TData): Boolean {
        return data.remove(`object`)
    }

    open fun remove(position: Int): TData {
        return data.removeAt(position)
    }

    open fun updateItems(newObjects: List<TData>, callback: DiffUtil.Callback) {
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
        data = newObjects.toMutableList()
    }

    open fun replaceItems(newObjects: List<TData>) {
        data = newObjects.toMutableList()
        notifyDataSetChanged()
    }

    open fun clear() {
        data.clear()
    }

    open fun addAll(collection: Collection<TData>): Boolean {
        return data.addAll(collection)
    }

    open fun getItemPosition(`object`: TData): Int {
        return data.indexOf(`object`)
    }

    open fun insert(`object`: TData, position: Int) {
        data.add(position, `object`)
    }

    open fun insertAll(`object`: Collection<TData>, position: Int) {
        data.addAll(position, `object`)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }
}
