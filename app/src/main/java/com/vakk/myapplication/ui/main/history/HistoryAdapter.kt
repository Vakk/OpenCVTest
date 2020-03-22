package com.vakk.myapplication.ui.main.history

import android.view.ViewGroup
import com.vakk.myapplication.R
import com.vakk.myapplication.ui.list.adapter.AdapterClickListener
import com.vakk.myapplication.ui.list.base.BaseListenerRecyclerViewAdapter
import com.vakk.myapplication.ui.list.base.BaseViewHolder
import com.vakk.myapplication.ui.list.items.HistoryItemViewModel
import com.vakk.myapplication.ui.list.items.ViewHolderType
import com.vakk.myapplication.ui.list.viewholder.HistoryViewHolder

class HistoryAdapter(listener: AdapterClickListener<HistoryItemViewModel>) :
    BaseListenerRecyclerViewAdapter<HistoryItemViewModel, BaseViewHolder<HistoryItemViewModel>>(
        listener = listener
    ) {
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<HistoryItemViewModel> {
        return when (ViewHolderType[viewType]) {
            ViewHolderType.HISTORY -> HistoryViewHolder(
                initView(R.layout.item_history, parent),
                this
            )
        }
    }

}