package com.example.currencyconverter.di

import com.example.currencyconverter.currencyList.data.CurrencyListApi
import com.example.currencyconverter.currencyList.data.CurrencyListRepositoryImpl
import com.example.currencyconverter.currencyList.domain.CurrencyListRepository
import com.example.currencyconverter.shared.SharedViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): CurrencyListApi {
        return Retrofit.Builder()
            .baseUrl("https://api.frankfurter.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(CurrencyListApi::class.java)
    }

    @Provides
    @Named("io")
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named("main")
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    fun provideSharedViewModel(): SharedViewModel = SharedViewModel()
}

@Module
abstract class AppBindModule {
    @Binds
    abstract fun bindRepository(repositoryImpl: CurrencyListRepositoryImpl): CurrencyListRepository
}
