package com.example.currencyconverter.currencyList.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.currencyList.domain.CurrencyDomainModel
import com.example.currencyconverter.currencyList.domain.GetLatestRatesUseCase
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
) : ViewModel() {

    private val _currencyList = MutableLiveData<List<CurrencyDomainModel>>()
    val currencyList: LiveData<List<CurrencyDomainModel>> = _currencyList
    private val compositeDisposable by lazy { CompositeDisposable() }

    fun getCurrencyList() {
        getLatestRatesUseCase()
            .subscribeOn(ioSchedulers)
            .observeOn(mainSchedulers)
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
