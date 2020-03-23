package com.vakk.myapplication.dagger

import androidx.lifecycle.ViewModel
import com.vakk.core.dagger.module.ViewModelModule
import com.vakk.myapplication.ui.main.MainViewModel
import com.vakk.myapplication.ui.main.camera.CameraViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CameraViewModel::class)
    abstract fun cameraViewModel(viewModel: CameraViewModel): ViewModel
}