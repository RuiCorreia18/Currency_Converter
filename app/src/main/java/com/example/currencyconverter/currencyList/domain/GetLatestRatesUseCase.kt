package com.example.currencyconverter.currencyList.domain

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetLatestRatesUseCase @Inject constructor(
    private val repository: CurrencyListRepository
) {
    operator fun invoke(): Single<List<CurrencyDomainModel>> = repository.getLatestRates()
}
