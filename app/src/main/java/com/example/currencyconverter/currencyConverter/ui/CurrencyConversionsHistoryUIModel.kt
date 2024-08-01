package com.example.currencyconverter.currencyConverter.ui

data class CurrencyConversionsHistoryUIModel(
    val fromCurrency: String = "",
    val fromValue: String = "",
    val toCurrency: String = ""
) {
    override fun toString(): String {
        return if(fromCurrency.isNotBlank()){
            "$fromValue $fromCurrency to $toCurrency"
        }else{
            ""
        }
    }
}
