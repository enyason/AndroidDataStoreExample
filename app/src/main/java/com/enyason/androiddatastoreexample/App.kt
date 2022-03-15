package com.enyason.androiddatastoreexample

import android.app.Application
import com.enyason.androiddatastoreexample.di.dataAccessModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataAccessModule)
        }
    }
}