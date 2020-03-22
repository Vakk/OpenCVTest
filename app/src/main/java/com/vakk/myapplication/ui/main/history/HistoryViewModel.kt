package com.vakk.myapplication.ui.main.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vakk.myapplication.ui.list.items.HistoryItemViewModel
import javax.inject.Inject

class HistoryViewModel @Inject constructor(application: Application) :
    BaseAnd(application) {
    val onItemsUpdate = MutableLiveData<HistoryItemViewModel>()

    init {

    }
}