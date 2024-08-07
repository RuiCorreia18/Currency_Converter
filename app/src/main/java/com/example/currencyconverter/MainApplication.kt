package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.di.AppComponent
import com.example.currencyconverter.di.DaggerAppComponent
import javax.inject.Inject

class MainApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    @Inject
    lateinit var appDatabase: CurrencyConverterDatabase

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
    }
}
