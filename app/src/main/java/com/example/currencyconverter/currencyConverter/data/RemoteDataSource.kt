package com.example.currencyconverter.currencyConverter.data

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val converterApi: CurrencyConvertorApi
) {
    fun getConvertedCurrency(
        from: String,
        to: String,
        amount: Double
    ): Single<CurrencyConverterResponse> = converterApi.getConvertedCurrency(from, to, amount)
}
