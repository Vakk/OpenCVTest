package com.vakk.myapplication.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    fun test() {
        Log.d("TAg", "testContext")
    }
}