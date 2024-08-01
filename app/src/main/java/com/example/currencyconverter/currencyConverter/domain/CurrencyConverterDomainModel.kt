package com.example.currencyconverter.currencyConverter.domain

data class CurrencyConverterDomainModel(
    val fromCurrency: String,
    val fromValue: Double,
    val toCurrency: String,
)
