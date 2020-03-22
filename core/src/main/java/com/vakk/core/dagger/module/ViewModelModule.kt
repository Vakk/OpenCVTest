package com.vakk.core.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.vakk.core.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}