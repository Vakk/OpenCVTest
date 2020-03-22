package com.vakk.myapplication.ui.list.viewholder

import android.view.View
import android.widget.ImageView
import com.vakk.myapplication.R
import com.vakk.myapplication.ui.list.adapter.AdapterClickListener
import com.vakk.myapplication.ui.list.base.BaseViewHolder
import com.vakk.myapplication.ui.list.items.HistoryItemViewModel
import com.vakk.myapplication.utils.loadImage

class HistoryViewHolder(
    view: View,
    listener: AdapterClickListener<HistoryItemViewModel>
) : BaseViewHolder<HistoryItemViewModel>(
    view,
    listener
) {

    val ivImage = view.findViewById<ImageView>(R.id.ivImage)

    override fun onBind(item: HistoryItemViewModel) {
        ivImage.loadImage(item.url)
    }
}