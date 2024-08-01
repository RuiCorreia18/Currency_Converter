package com.example.currencyconverter.currencyConverter.data.remote

import com.example.currencyconverter.currencyConverter.data.CurrencyConverterApi
import com.example.currencyconverter.currencyConverter.data.CurrencyConverterResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val converterApi: CurrencyConverterApi
) {
    fun getConvertedCurrency(
        from: String,
        to: String,
        amount: Double
    ): Single<CurrencyConverterResponse> = converterApi.getConvertedCurrency(from, to, amount)
}
