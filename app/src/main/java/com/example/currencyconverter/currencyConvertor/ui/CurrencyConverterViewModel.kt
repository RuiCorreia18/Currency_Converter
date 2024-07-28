package com.example.currencyconverter.currencyConvertor.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.currencyList.domain.GetLatestRatesUseCase
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class CurrencyConverterViewModel @Inject constructor(
    @Named("io") private val ioSchedulers: Scheduler,
    @Named("main") private val mainSchedulers: Scheduler,
    private val getLatestRatesUseCase: GetLatestRatesUseCase,
): ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _currencyList = MutableLiveData<List<String>>()
    val currencyList: LiveData<List<String>> = _currencyList

    //TODO Replace getCurrencyList from values from CurrencyListViewModel if possible
    init {
        getCurrencyList()
    }

    private fun getCurrencyList() {
        getLatestRatesUseCase()
            .subscribeOn(ioSchedulers)
            .observeOn(mainSchedulers)
            .subscribeBy(
                onSuccess = { rates ->
                    _currencyList.value = rates.map { it.code }
                }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
