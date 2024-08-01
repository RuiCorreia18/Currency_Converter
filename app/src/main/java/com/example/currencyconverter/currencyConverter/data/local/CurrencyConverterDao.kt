package com.example.currencyconverter.currencyConverter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CurrencyConverterDao {

    @Query("SELECT * FROM currencyConverter")
    fun getCurrencyConverterHistory(): Single<List<CurrencyConverterEntity>>

    @Insert
    fun insertCurrencyConversion(currencyConversion: CurrencyConverterEntity): Completable
}
