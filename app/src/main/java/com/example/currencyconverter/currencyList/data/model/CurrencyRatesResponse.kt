package com.example.currencyconverter.currencyList.data.model

data class CurrencyRatesResponse(
    val base: String,
    val amount: Double,
    val rates: Map<String, Double>
)
