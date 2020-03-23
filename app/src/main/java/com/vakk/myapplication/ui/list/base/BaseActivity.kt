package com.vakk.myapplication.ui.list.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel>(
    private val vmClass: Class<VM>,
    private val layoutId: Int
) :
    DaggerAppCompatActivity() {

    lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        viewModel = ViewModelProvider(this, viewModelFactory)[vmClass]
    }
}
