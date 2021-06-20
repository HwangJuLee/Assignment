package com.lhj.assignment

import android.app.Application
import com.lhj.assignment.DI.mainDiModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, mainDiModule)
    }
}