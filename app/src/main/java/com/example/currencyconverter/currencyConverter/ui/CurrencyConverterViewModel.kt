package com.example.currencyconverter.currencyConverter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.currencyConverter.domain.useCases.GetCurrencyConversionUseCase
import com.example.currencyconverter.shared.SharedViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.text.DecimalFormat
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

    private val _currencyConversion = MutableLiveData<String>()
    val currencyConversion: LiveData<String> = _currencyConversion

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        //Get currency list fetched on currencyList from shared view model
        _currencyList.value = sharedViewModel.getCurrencyListLiveData().value
    }

    fun getCurrencyConversion(from: String, to: String, value: String) {
        currencyConversionUseCase(from, to, value.toDouble())
            .subscribeOn(ioSchedulers)
            .observeOn(mainSchedulers)
            .subscribeBy(
                onSuccess = {
                    _currencyConversion.value = DecimalFormat("#,##0.00").format(it)
                },
                onError = { _errorMessage.value = "Error converting currencies" }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
