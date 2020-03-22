package com.vakk.myapplication.ui.list.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vakk.myapplication.ui.list.adapter.AdapterClickListener
import java.lang.ref.WeakReference

abstract class BaseViewHolder<T>(view: View, listener: AdapterClickListener<T>) :
    RecyclerView.ViewHolder(view) {

    companion object {
        fun initView(layoutId: Int, parent: ViewGroup): View {
            return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        }
    }

    protected var item: T
        get() = itemView.tag as T
        set(value) {
            itemView.tag = value
        }

    protected var optItem: T?
        get() = itemView.tag as? T?
        set(value) {
            itemView.tag = value
        }

    val context: Context
        get() = itemView.context

    private var listenerWR = WeakReference(listener)

    protected val listener: AdapterClickListener<T>?
        get() = listenerWR.get()

    fun bind(item: T) {
        this.item = item
        onBind(item)
    }

    fun bind(item: T, payloads: Bundle?) {
        this.item = item
        if (payloads == null || !onBind(item, payloads)) {
            onBind(item)
        }
    }

    abstract fun onBind(item: T)

    /**
     * Override this and return true if you want to call this when something was changed with payload.
     * If you will return false - you will receive onBind(item: T) notification.
     */
    open fun onBind(item: T, payloads: Bundle): Boolean {
        // You should override this for bind with payloads.
        return false
    }
}