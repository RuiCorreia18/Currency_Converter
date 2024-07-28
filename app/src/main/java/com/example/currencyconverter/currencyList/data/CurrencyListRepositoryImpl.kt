package com.example.currencyconverter.currencyList.data

import com.example.currencyconverter.currencyList.domain.CurrencyDomainModel
import com.example.currencyconverter.currencyList.domain.CurrencyListRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CurrencyListRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CurrencyListRepository {
    override fun getLatestRates(): Single<List<CurrencyDomainModel>> {
        return remoteDataSource.getLatestRates()
            .map { response ->
                //Gets Base currency as first element
                val baseCurrency = CurrencyDomainModel(response.base, response.amount)
                val currenciesList = response.rates.map { (currency, rate) ->
                    CurrencyDomainModel(currency, rate)
                }

                //Append base currency with rest of currencies
                listOf(baseCurrency) + currenciesList
            }
    }

}
