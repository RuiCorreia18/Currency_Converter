package com.example.currencyconverter.currencyConverter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.currencyConverter.domain.CurrencyConverterDomainModel
import com.example.currencyconverter.currencyConverter.domain.GetCurrencyConversionUseCase
import com.example.currencyconverter.shared.SharedViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class CurrencyConverterViewModel @Inject constructor(
    @Named("io") private val ioSchedulers: Scheduler,
    @Named("main") private val mainSchedulers: Scheduler,
    sharedViewModel: SharedViewModel,
    private val currencyConversionUseCase: GetCurrencyConversionUseCase,
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _currencyList = MutableLiveData<List<String>>()
    val currencyList: LiveData<List<String>> = _currencyList

    private val _currencyConversion = MutableLiveData<CurrencyConverterDomainModel>()
    val currencyConversion: LiveData<CurrencyConverterDomainModel> = _currencyConversion

    init {
        //Get currency list fetched on currencyList from sharedview model
        _currencyList.value = sharedViewModel.getCurrencyListLiveData().value
        sharedViewModel.setCurrencyListLiveData(emptyList())
    }

    fun getCurrencyConversion(from: String, to: String, value: String) {
        currencyConversionUseCase(from, to, value.toDouble())
            .subscribeOn(ioSchedulers)
            .observeOn(mainSchedulers)
            .subscribeBy(
                onSuccess = {
                    _currencyConversion.value = it
                }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}