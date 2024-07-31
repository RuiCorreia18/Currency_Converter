package com.example.currencyconverter.currencyList.ui

import com.example.currencyconverter.currencyList.domain.CurrencyDomainModel
import java.text.DecimalFormat
import java.util.Currency

fun CurrencyDomainModel.toUIModel(): CurrencyUIModel{
    val rate = DecimalFormat("#,##0.00").format(this.rate)
    val currency: Currency = Currency.getInstance(this.code)
    val symbol: String = currency.symbol
    return CurrencyUIModel(
        code = this.code,
        rate = rate,
        symbol = symbol
    )
}
