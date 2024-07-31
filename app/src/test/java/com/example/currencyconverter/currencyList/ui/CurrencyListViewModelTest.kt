package com.example.currencyconverter.currencyList.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.currencyconverter.currencyList.domain.CurrencyDomainModel
import com.example.currencyconverter.currencyList.domain.GetLatestRatesUseCase
import com.example.currencyconverter.shared.SharedViewModel
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CurrencyListViewModelTest {
    private val getLatestRatesUseCase: GetLatestRatesUseCase = mockk()
    private val ioSchedulers: Scheduler = Schedulers.trampoline()
    private val mainSchedulers: Scheduler = Schedulers.trampoline()
    //relaxed used so i dont need to configure all functions of sharedviewmodel
    private val sharedViewModel: SharedViewModel = mockk(relaxed = true)


    private val viewModel = CurrencyListViewModel(
        getLatestRatesUseCase,
        ioSchedulers,
        mainSchedulers,
        sharedViewModel
    )

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `when getLatestRates success should fill livedata`() {
        val expected = listOf(
            CurrencyDomainModel("EUR", 1.00),
            CurrencyDomainModel("USD", 1.20),
            CurrencyDomainModel("GBP", 1.30),
        )

        every { getLatestRatesUseCase() } returns Single.just(expected)

        viewModel.getCurrencyList()

        assertEquals(expected, viewModel.currencyList.value)
    }

    @Test
    fun `when getLatestRates failure should trigger error message`() {
        val errorMessage = "Error getting list of currencies"
        every { getLatestRatesUseCase() } returns Single.error(Exception(errorMessage))

        val errorMessageObserver: Observer<String> = mockk()
        viewModel.errorMessage.observeForever(errorMessageObserver)
        viewModel.getCurrencyList()

        verify { sharedViewModel wasNot Called }
        verify { errorMessageObserver.onChanged(errorMessage) }
    }

    @Test
    fun `when searchCurrency string is not empty should filter currency list`(){
        val initialList = listOf(
            CurrencyDomainModel("USD", 1.0),
            CurrencyDomainModel("EUR", 1.0),
            CurrencyDomainModel("GBP", 1.0)
        )
        //Init currencyList
        every { getLatestRatesUseCase() } returns Single.just(initialList)
        viewModel.getCurrencyList()

        viewModel.searchCurrency("US")

        val expectedList = listOf(CurrencyDomainModel("USD", 1.0))
        assertEquals(expectedList, viewModel.currencyList.value)
    }

    @Test
    fun `when searchCurrency string is empty should return full list`(){
        val initialList = listOf(
            CurrencyDomainModel("USD", 1.0),
            CurrencyDomainModel("EUR", 1.0),
            CurrencyDomainModel("GBP", 1.0)
        )
        //Init currencyList
        every { getLatestRatesUseCase() } returns Single.just(initialList)
        viewModel.getCurrencyList()

        viewModel.searchCurrency("")

        assertEquals(initialList, viewModel.currencyList.value)
    }

    @Test
    fun `when searchCurrency string is null should return full list`(){
        val initialList = listOf(
            CurrencyDomainModel("USD", 1.0),
            CurrencyDomainModel("EUR", 1.0),
            CurrencyDomainModel("GBP", 1.0)
        )
        //Init currencyList
        every { getLatestRatesUseCase() } returns Single.just(initialList)
        viewModel.getCurrencyList()

        viewModel.searchCurrency(null)

        assertEquals(initialList, viewModel.currencyList.value)
    }

    @Test
    fun `when searchCurrency string not a currency should return empty list`(){
        val initialList = listOf(
            CurrencyDomainModel("USD", 1.0),
            CurrencyDomainModel("EUR", 1.0),
            CurrencyDomainModel("GBP", 1.0)
        )
        //Init currencyList
        every { getLatestRatesUseCase() } returns Single.just(initialList)
        viewModel.getCurrencyList()

        viewModel.searchCurrency("CURRENCY")

        val expected = emptyList<CurrencyDomainModel>()
        assertEquals(expected, viewModel.currencyList.value)
    }
}
