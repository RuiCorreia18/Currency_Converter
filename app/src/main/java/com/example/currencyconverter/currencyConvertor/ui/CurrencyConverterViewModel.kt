package com.example.currencyconverter.currencyConvertor.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.shared.SharedViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class CurrencyConverterViewModel @Inject constructor(
    @Named("io") private val ioSchedulers: Scheduler,
    @Named("main") private val mainSchedulers: Scheduler,
    sharedViewModel: SharedViewModel
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _currencyList = MutableLiveData<List<String>>()
    val currencyList: LiveData<List<String>> = _currencyList

    private val _currencyConvertion = MutableLiveData<List<String>>()
    val currencyConvertion: LiveData<List<String>> = _currencyConvertion

    //TODO Replace getCurrencyList from values from CurrencyListViewModel if possible
    init {
        //Get currency list fetched on currencyList from sharedview model
        _currencyList.value = sharedViewModel.getCurrencyListLiveData().value
        sharedViewModel.setCurrencyListLiveData(emptyList())
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
