package com.example.currencyconverter.currencyConverter.domain

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCurrencyConversionUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    operator fun invoke(from: String, to: String, amount: Double): Single<CurrencyConverterDomainModel> =
        repository.getCurrencyConversion(from, to, amount)
}