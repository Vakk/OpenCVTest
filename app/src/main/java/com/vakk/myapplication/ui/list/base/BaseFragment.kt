package com.vakk.myapplication.ui.list.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel>(
    private val vmClass: Class<VM>,
    @LayoutRes private val layoutId: Int
) : DaggerFragment() {

    lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected var liveData = mutableListOf<LiveData<*>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[vmClass]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPrepareObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val oldList = liveData.toList()
        liveData = mutableListOf()
        oldList.forEach { it.removeObservers(this) }
    }

    open fun onPrepareObservers() {
    }

    /**
     * Take control to the live data lifecycle.
     */
    protected inline fun <reified T> LiveData<T>.observe(observer: Observer<T>) {
        observeForever(observer)
        liveData.add(this)
    }
}
