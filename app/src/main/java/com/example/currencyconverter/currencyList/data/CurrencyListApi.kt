package com.example.currencyconverter.currencyList.data

import com.example.currencyconverter.currencyList.data.model.CurrencyRatesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CurrencyListApi {

    @GET("latest")
    fun getLatestRates(): Single<List<CurrencyRatesResponse>>
}
