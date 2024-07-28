package com.example.currencyconverter.currencyList.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyRatesResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("rates")
    val rates: Map<String, Double>
)
