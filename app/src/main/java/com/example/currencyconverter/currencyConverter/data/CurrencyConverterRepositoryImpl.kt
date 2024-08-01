package com.example.currencyconverter.currencyConverter.data

import com.example.currencyconverter.currencyConverter.data.local.CurrencyConverterEntity
import com.example.currencyconverter.currencyConverter.data.local.LocalDataSource
import com.example.currencyconverter.currencyConverter.data.local.toDomainModel
import com.example.currencyconverter.currencyConverter.data.remote.RemoteDataSource
import com.example.currencyconverter.currencyConverter.domain.CurrencyConverterDomainModel
import com.example.currencyconverter.currencyConverter.domain.CurrencyConverterRepository
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

    override fun getCurrencyConverterHistory(): Single<List<CurrencyConverterDomainModel>> {
        return localDataSource.getCurrencyConverterHistory()
            .map { history ->
                history.toDomainModel()
            }
    }

    override fun insertCurrencyConversion(
        from: String,
        to: String,
        value: Double
    ): Single<CurrencyConverterDomainModel> {
        val historyToInsert = CurrencyConverterEntity(
            fromCurrency = from,
            fromValue = value,
            toCurrency = to,
        )

        return localDataSource.insertCurrencyConversion(historyToInsert).toSingle {
            historyToInsert.toDomainModel()
        }
    }
}
