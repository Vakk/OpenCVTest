package com.vakk.myapplication.ui.main.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vakk.myapplication.R
import com.vakk.myapplication.ui.list.adapter.AdapterClickListener
import com.vakk.myapplication.ui.list.base.BaseFragment
import com.vakk.myapplication.ui.list.items.HistoryItemViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class HistoryFragment :
    BaseFragment<HistoryViewModel>(HistoryViewModel::class.java, R.layout.fragment_list),
    AdapterClickListener<HistoryItemViewModel> {

    private val adapter by lazy { HistoryAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    override fun onClick(item: HistoryItemViewModel, view: View) {
        //TODO: Add details view.
    }

    override fun onPrepareObservers() {
        super.onPrepareObservers()
        viewModel.onItemsUpdate.observe(Observer {
            adapter.replaceItems(it)
        })
    }

    private fun initList() {
        rvItems.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        rvItems.adapter = adapter
    }

}