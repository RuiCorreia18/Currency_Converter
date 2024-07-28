package com.example.currencyconverter.currencyList.domain

import io.reactivex.rxjava3.core.Single

interface CurrencyListRepository {
    fun getLatestRates(): Single<List<CurrencyDomainModel>>
}
