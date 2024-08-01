package com.example.currencyconverter.currencyConverter.domain

import io.reactivex.rxjava3.core.Single

interface CurrencyConverterRepository {
    fun getCurrencyConversion(from: String, to: String, amount: Double): Single<Double>
    fun getCurrencyConverterHistory(): Single<List<CurrencyConverterDomainModel>>
    fun insertCurrencyConversion(
        from: String,
        to: String,
        value: Double
    ): Single<CurrencyConverterDomainModel>
}
