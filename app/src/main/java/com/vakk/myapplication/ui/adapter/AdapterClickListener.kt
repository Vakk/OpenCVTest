package com.vakk.myapplication.ui.adapter

import android.view.View

interface AdapterClickListener<in T> {
    fun onClick(item: T,view: View)
}