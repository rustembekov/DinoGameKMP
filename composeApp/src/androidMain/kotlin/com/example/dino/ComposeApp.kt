package com.example.dino

import android.app.Application
import di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ComposeApp)
            modules(sharedModule)
        }
    }
}