package com.vakk.myapplication.dagger

import android.app.Application
import com.vakk.core.dagger.module.MainModule
import com.vakk.core.dagger.scopes.MainScope
import com.vakk.myapplication.ui.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @Binds
    abstract fun application(application: DaggerApplication): Application

    @ContributesAndroidInjector(
        modules = [
            MainModule::class,
            MainViewModelModule::class,
            MainInjectorsModule::class
        ]
    )
    @MainScope
    abstract fun contributeMainActivity(): MainActivity
}