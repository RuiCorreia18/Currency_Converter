package com.example.currencyconverter.currencyList.data

import com.example.currencyconverter.currencyList.data.model.CurrencyRatesResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val currencyListApi: CurrencyListApi
) {
    fun getLatestRates(): Single<CurrencyRatesResponse> = currencyListApi.getLatestRates()
}
