package com.example.currencyconverter.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {
    private val currencyList = MutableLiveData<List<String>>()

    fun getCurrencyListLiveData(): LiveData<List<String>> {
        return currencyList
    }

    fun setCurrencyListLiveData(currencies: List<String>) {
        currencyList.value = currencies
    }
}
