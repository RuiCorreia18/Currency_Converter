package com.example.currencyconverter.currencyConverter.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterApi {

    @GET("latest")
    fun getConvertedCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Single<CurrencyConverterResponse>
}
