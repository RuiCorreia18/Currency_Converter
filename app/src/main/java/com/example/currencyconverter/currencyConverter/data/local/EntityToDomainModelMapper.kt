package com.example.currencyconverter.currencyConverter.data.local

import com.example.currencyconverter.currencyConverter.domain.CurrencyConverterDomainModel

fun List<CurrencyConverterEntity>.toDomainModel(): List<CurrencyConverterDomainModel> {
    return this.map {
        CurrencyConverterDomainModel(
            fromCurrency = it.fromCurrency,
            fromValue = it.fromValue,
            toCurrency = it.toCurrency,
        )
    }
}

fun CurrencyConverterEntity.toDomainModel(): CurrencyConverterDomainModel {
    return CurrencyConverterDomainModel(
        fromCurrency = this.fromCurrency,
        fromValue = this.fromValue,
        toCurrency = this.toCurrency,
    )
}
