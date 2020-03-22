package com.vakk.myapplication.ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.vakk.myapplication.ui.adapter.AdapterClickListener
import java.lang.ref.WeakReference

abstract class BaseListenerRecyclerViewAdapter<T, VH : BaseViewHolder<T>>(
    items: List<T> = mutableListOf(),
    listener: AdapterClickListener<T>? = null
) : BaseRecyclerViewAdapter<T, VH>(items), AdapterClickListener<T> {

    companion object {
        fun initView(layoutId: Int, parent: ViewGroup): View {
            return BaseViewHolder.initView(layoutId, parent)
        }
    }

    protected var listenerWR: WeakReference<AdapterClickListener<T>?> = WeakReference(listener)

    override fun onClick(item: T, view: View) {
        listenerWR.get()?.onClick(item, view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        val payload = payloads.firstOrNull() as? Bundle
        if (payload == null) {
            onBindViewHolder(holder, position)
        } else {
            holder.bind(getItem(position), payload)
        }
    }
}