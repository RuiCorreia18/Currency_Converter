package com.example.currencyconverter.currencyConverter.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "currencyConverter")
data class CurrencyConverterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val fromCurrency: String,
    val fromValue : Double,
    val toCurrency: String,
)
