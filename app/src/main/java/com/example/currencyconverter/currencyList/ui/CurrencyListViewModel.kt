package com.example.currencyconverter.currencyList.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.currencyList.data.CurrencyListApi
import com.example.currencyconverter.currencyList.domain.CurrencyDomainModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(
    private val retrofit: CurrencyListApi
) : ViewModel() {

    private val _currencyList = MutableLiveData<List<CurrencyDomainModel>>()
    val currencyList: LiveData<List<CurrencyDomainModel>> = _currencyList

    fun getCurrencyList() {
        retrofit.getLatestRates()
            .map { response ->
                response.rates.map { (currency, value) ->
                    CurrencyDomainModel(currency, value)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { rates ->
                    _currencyList.value = rates
                }
            )
    }
}
