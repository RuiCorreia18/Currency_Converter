package com.example.currencyconverter.currencyConverter.domain.useCases

import com.example.currencyconverter.currencyConverter.domain.CurrencyConverterRepository
import com.example.currencyconverter.currencyConverter.ui.CurrencyConversionsHistoryUIModel
import com.example.currencyconverter.currencyConverter.ui.toUIModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ManageCurrencyConversionHistoryUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    fun getCurrencyConversionHistory(): Single<List<CurrencyConversionsHistoryUIModel>> {
        return repository.getCurrencyConverterHistory().map {
            it.toUIModel()
        }
    }

    fun insertCurrencyConversion(
        from: String,
        to: String,
        value: String
    ): Single<CurrencyConversionsHistoryUIModel> {
        return repository.insertCurrencyConversion(from, to, value.toDouble()).map { it.toUIModel() }
    }
}
