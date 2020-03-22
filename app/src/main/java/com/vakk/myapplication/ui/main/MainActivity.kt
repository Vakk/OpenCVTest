package com.vakk.myapplication.ui.main

import android.os.Bundle
import com.vakk.myapplication.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.test()
    }


}