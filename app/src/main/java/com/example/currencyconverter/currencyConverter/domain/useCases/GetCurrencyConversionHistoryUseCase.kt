package com.example.currencyconverter.currencyConverter.domain.useCases

import com.example.currencyconverter.currencyConverter.domain.CurrencyConverterRepository
import javax.inject.Inject

class ManageCurrencyConversionHistoryUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    fun getCurrencyConversionHistory() = repository.getCurrencyConverterHistory()
    fun insertCurrencyConversion() = repository.insertCurrencyConversion()
}
