package com.example.currencyconverter.currencyConverter.data

import com.example.currencyconverter.currencyConverter.data.local.CurrencyConverterEntity
import com.example.currencyconverter.currencyConverter.data.local.LocalDataSource
import com.example.currencyconverter.currencyConverter.data.remote.RemoteDataSource
import com.example.currencyconverter.currencyConverter.domain.CurrencyConverterRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CurrencyConverterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : CurrencyConverterRepository {
    override fun getCurrencyConversion(
        from: String,
        to: String,
        amount: Double
    ): Single<Double> {
        return remoteDataSource.getConvertedCurrency(from, to, amount)
            .map { conversion ->
                conversion.rates[to] ?: 0.0
            }
    }

    override fun getCurrencyConverterHistory(): Single<List<CurrencyConverterEntity>> {
        return localDataSource.getCurrencyConverterHistory()
    }

    override fun insertCurrencyConversion(): Completable {
        return localDataSource.insertCurrencyConversion()
    }
}
