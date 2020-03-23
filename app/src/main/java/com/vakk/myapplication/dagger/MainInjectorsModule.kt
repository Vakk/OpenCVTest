package com.vakk.myapplication.dagger

import com.vakk.myapplication.ui.main.camera.CameraFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainInjectorsModule {

    @ContributesAndroidInjector
    abstract fun cameraFragment(): CameraFragment
}