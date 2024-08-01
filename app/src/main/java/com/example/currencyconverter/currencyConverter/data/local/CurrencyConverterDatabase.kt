package com.example.currencyconverter.currencyConverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyConverterEntity::class], version = 1, exportSchema = false)
abstract class CurrencyConverterDatabase: RoomDatabase() {
    abstract fun currencyConverterDao(): CurrencyConverterDao
}
