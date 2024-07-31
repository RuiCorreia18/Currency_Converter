package com.example.currencyconverter.currencyConverter.data

import com.google.gson.annotations.SerializedName

data class CurrencyConverterResponse(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: Map<String, Double>
)
