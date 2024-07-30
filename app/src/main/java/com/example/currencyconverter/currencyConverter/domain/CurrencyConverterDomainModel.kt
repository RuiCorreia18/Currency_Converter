package com.example.currencyconverter.currencyConverter.domain

data class CurrencyConverterDomainModel(
    val fromCurrencyCode: String,
    val fromCurrencyValue: Double,
    val toCurrencyCode: String,
    val toCurrencyValue: Double
)
