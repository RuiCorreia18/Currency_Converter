package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.di.AppComponent
import com.example.currencyconverter.di.DaggerAppComponent

class MainApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
    }
}
