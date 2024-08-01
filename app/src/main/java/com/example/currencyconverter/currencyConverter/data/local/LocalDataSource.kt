package com.example.currencyconverter.currencyConverter.data.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val currencyConverterDao: CurrencyConverterDao
) {
    fun getCurrencyConverterHistory(): Single<List<CurrencyConverterEntity>> =
        currencyConverterDao.getCurrencyConverterHistory()

    fun insertCurrencyConversion(
        historyToInsert: CurrencyConverterEntity
    ): Completable {
        return Completable.fromAction {
            currencyConverterDao.insertCurrencyConversion(
                historyToInsert
            )
        }
    }
}
