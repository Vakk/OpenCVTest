package com.vakk.myapplication.ui.main

import android.os.Bundle
import com.vakk.myapplication.R
import com.vakk.myapplication.ui.list.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class.java, R.layout.activity_container) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.test()
    }


}