package com.example.currencyconverter.di

import com.example.currencyconverter.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)
}
