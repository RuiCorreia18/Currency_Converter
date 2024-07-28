package com.example.currencyconverter.di

import com.example.currencyconverter.currencyList.data.CurrencyListApi
import com.example.currencyconverter.currencyList.data.CurrencyListRepositoryImpl
import com.example.currencyconverter.currencyList.domain.CurrencyListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
}

@Module
abstract class AppBindModule {
    @Binds
    abstract fun bindRepository(repositoryImpl: CurrencyListRepositoryImpl): CurrencyListRepository
}
