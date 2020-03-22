package com.vakk.myapplication.ui.main.history

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.vakk.myapplication.ui.list.base.BaseViewModel
import com.vakk.myapplication.ui.list.base.PaginationViewModel
import com.vakk.myapplication.ui.list.items.HistoryItemViewModel
import javax.inject.Inject

class HistoryViewModel @Inject constructor(application: Application) : BaseViewModel(application),
    PaginationViewModel {

    override var isLastPage: Boolean = false
    override val isPaginationInProcess: Boolean = false
    override var itemsPerPage: Int = 3

    val onItemsUpdate = MutableLiveData<List<HistoryItemViewModel>>()

    override fun loadMoreItems() {
        val itemsPerPage = itemsPerPage
        runOnBackground(onError) {
            isLastPage = true
        }
    }

}