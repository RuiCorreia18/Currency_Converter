package com.example.currencyconverter.currencyConverter.data

import com.example.currencyconverter.currencyConverter.domain.CurrencyConverterDomainModel
import com.example.currencyconverter.currencyConverter.domain.CurrencyConverterRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CurrencyConverterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CurrencyConverterRepository {
    override fun getCurrencyConversion(
        from: String,
        to: String,
        amount: Double
    ): Single<CurrencyConverterDomainModel> {
        return remoteDataSource.getConvertedCurrency(from, to, amount)
            .map { conversion ->
                CurrencyConverterDomainModel(from, amount, to, conversion.rates[to] ?: 0.0)
            }
    }
}
