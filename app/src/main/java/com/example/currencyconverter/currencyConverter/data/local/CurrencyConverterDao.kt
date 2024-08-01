package com.example.currencyconverter.currencyConverter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface CurrencyConverterDao {

    @Query("SELECT * FROM currencyConverter ORDER BY id DESC")
    fun getCurrencyConverterHistory(): Single<List<CurrencyConverterEntity>>

    @Insert
    fun insertCurrencyConversion(currencyConversion: CurrencyConverterEntity)
}
