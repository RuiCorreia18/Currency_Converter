package com.example.currencyconverter.di

import android.app.Application
import com.example.currencyconverter.MainApplication
import com.example.currencyconverter.currencyList.ui.CurrencyListFragment
import com.example.currencyconverter.di.viewModel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        AppBindModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

    fun inject(app: MainApplication)
    fun inject(fragment: CurrencyListFragment)
}
