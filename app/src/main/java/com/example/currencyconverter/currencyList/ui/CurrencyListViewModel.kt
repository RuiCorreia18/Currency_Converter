package com.example.currencyconverter.currencyList.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.currencyList.domain.CurrencyDomainModel
import com.example.currencyconverter.currencyList.domain.GetLatestRatesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(
    private val getLatestRatesUseCase: GetLatestRatesUseCase
) : ViewModel() {

    private val _currencyList = MutableLiveData<List<CurrencyDomainModel>>()
    val currencyList: LiveData<List<CurrencyDomainModel>> = _currencyList
    private val compositeDisposable by lazy { CompositeDisposable() }

    fun getCurrencyList() {
        getLatestRatesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { rates ->
                    _currencyList.value = rates
                }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
