package com.example.currencyconverter.currencyConverter.ui

import com.example.currencyconverter.currencyConverter.domain.CurrencyConverterDomainModel
import java.text.DecimalFormat

fun List<CurrencyConverterDomainModel>.toUIModel(): List<CurrencyConversionsHistoryUIModel> {
    return this.map {
        val value = DecimalFormat("#,##0.00").format(it.fromValue)
        CurrencyConversionsHistoryUIModel(
            fromCurrency = it.fromCurrency,
            fromValue = value,
            toCurrency = it.toCurrency
        )
    }
}

fun CurrencyConverterDomainModel.toUIModel(): CurrencyConversionsHistoryUIModel {
    val value = DecimalFormat("#,##0.00").format(this.fromValue)
    return CurrencyConversionsHistoryUIModel(
        fromCurrency = this.fromCurrency,
        fromValue = value,
        toCurrency = this.toCurrency
    )
}
