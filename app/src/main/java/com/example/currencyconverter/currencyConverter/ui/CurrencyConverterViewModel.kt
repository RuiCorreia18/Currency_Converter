package com.example.currencyconverter.currencyConverter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.currencyConverter.domain.useCases.GetCurrencyConversionUseCase
import com.example.currencyconverter.currencyConverter.domain.useCases.ManageCurrencyConversionHistoryUseCase
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
    private val manageCurrencyConversionHistoryUseCase: ManageCurrencyConversionHistoryUseCase,
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _currencyList = MutableLiveData<List<String>>()
    val currencyList: LiveData<List<String>> = _currencyList

    private val _conversionsHistoryList = MutableLiveData<List<CurrencyConversionsHistoryUIModel>>()
    val conversionsHistoryList: LiveData<List<CurrencyConversionsHistoryUIModel>> = _conversionsHistoryList

    private val _currencyConversion = MutableLiveData<String>()
    val currencyConversion: LiveData<String> = _currencyConversion

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        //Get currency list fetched on currencyList from shared view model
        _currencyList.value = sharedViewModel.getCurrencyListLiveData().value
        getCurrencyConversionHistory()
    }

    fun getCurrencyConversion(from: String, to: String, value: String) {
        currencyConversionUseCase(from, to, value)
            .subscribeOn(ioSchedulers)
            .observeOn(mainSchedulers)
            .subscribeBy(
                onSuccess = {
                    _currencyConversion.value = DecimalFormat("#,##0.00").format(it)
                    insertConversionToHistory(from, to, value)
                },
                onError = { _errorMessage.value = "Error converting currencies" }
            )
            .addTo(compositeDisposable)
    }

    private fun insertConversionToHistory(from: String, to: String, value: String){
        manageCurrencyConversionHistoryUseCase.insertCurrencyConversion(from, to, value)
            .subscribeOn(ioSchedulers)
            .observeOn(mainSchedulers)
            .subscribeBy(
                onSuccess = {
                    val historyList = if(_conversionsHistoryList.value.isNullOrEmpty()){
                        listOf(CurrencyConversionsHistoryUIModel(), it)
                    }else{
                        _conversionsHistoryList.value!! + listOf(it)
                    }

                    _conversionsHistoryList.value = historyList
                },
                onError = { _errorMessage.value = "Error adding to history" }
            )
            .addTo(compositeDisposable)
    }

    private fun getCurrencyConversionHistory() {
        manageCurrencyConversionHistoryUseCase.getCurrencyConversionHistory()
            .subscribeOn(ioSchedulers)
            .observeOn(mainSchedulers)
            .subscribeBy(
                onSuccess = { history ->
                    _conversionsHistoryList.value = history
                },
                onError = { _errorMessage.value = "Error getting history" }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
