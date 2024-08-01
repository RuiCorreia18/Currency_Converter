package com.example.currencyconverter.currencyConverter.domain

import com.example.currencyconverter.currencyConverter.data.local.CurrencyConverterEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CurrencyConverterRepository {
    fun getCurrencyConversion(from: String, to: String, amount: Double): Single<Double>
    fun getCurrencyConverterHistory(): Single<List<CurrencyConverterEntity>>
    fun insertCurrencyConversion(): Completable
}
