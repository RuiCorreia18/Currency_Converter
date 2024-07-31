package com.example.currencyconverter.currencyList.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.currencyList.domain.GetLatestRatesUseCase
import com.example.currencyconverter.shared.SharedViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class CurrencyListViewModel @Inject constructor(
    private val getLatestRatesUseCase: GetLatestRatesUseCase,
    @Named("io") private val ioSchedulers: Scheduler,
    @Named("main") private val mainSchedulers: Scheduler,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    private val _currencyList = MutableLiveData<List<CurrencyUIModel>>()
    val currencyList: LiveData<List<CurrencyUIModel>> = _currencyList

    private val tempCurrencyList = mutableListOf<CurrencyUIModel>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun getCurrencyList() {
        getLatestRatesUseCase()
            .subscribeOn(ioSchedulers)
            .observeOn(mainSchedulers)
            .subscribeBy(
                onSuccess = { rates ->
                    //Stores currency codes in shared view model so we can use it on the convertor
                    sharedViewModel.setCurrencyListLiveData(rates.map { it.code })
                    _currencyList.value = rates.map { it.toUIModel() }
                },
                onError = { _errorMessage.value = "Error getting list of currencies" }
            )
            .addTo(compositeDisposable)
    }

    fun searchCurrency(currency: String?) {
        val currencySearch = currency ?: ""
        if (tempCurrencyList.isEmpty()) currencyList.value?.let { tempCurrencyList.addAll(it) }
        _currencyList.value = tempCurrencyList.filter {
            it.code.lowercase().contains(currencySearch.lowercase())
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
