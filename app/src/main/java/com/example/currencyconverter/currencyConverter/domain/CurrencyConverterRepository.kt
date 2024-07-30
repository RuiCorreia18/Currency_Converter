package com.example.currencyconverter.currencyConverter.domain

import io.reactivex.rxjava3.core.Single

interface CurrencyConverterRepository {
    fun getCurrencyConversion(from: String, to: String, amount: Double): Single<Double>
}
