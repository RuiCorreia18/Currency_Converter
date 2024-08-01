package com.example.currencyconverter.currencyConverter.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.currencyconverter.currencyConverter.domain.useCases.GetCurrencyConversionUseCase
import com.example.currencyconverter.currencyConverter.domain.useCases.ManageCurrencyConversionHistoryUseCase
import com.example.currencyconverter.shared.SharedViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyConverterViewModelTest {
    private val currencyConversionUseCase: GetCurrencyConversionUseCase = mockk()
    private val manageCurrencyConversionHistoryUseCase: ManageCurrencyConversionHistoryUseCase = mockk()
    private val ioSchedulers: Scheduler = Schedulers.trampoline()
    private val mainSchedulers: Scheduler = Schedulers.trampoline()
    private val sharedViewModel: SharedViewModel = mockk()

    private lateinit var viewModel: CurrencyConverterViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        // Mocking the LiveData in SharedViewModel
        val currencyListLiveData = MutableLiveData<List<String>>()
        currencyListLiveData.value = listOf("EUR", "USD", "GBP")
        every { sharedViewModel.getCurrencyListLiveData() } returns currencyListLiveData
        every { manageCurrencyConversionHistoryUseCase.getCurrencyConversionHistory() } returns Single.just(emptyList())

        //need to setup viewmodel after all dependency are ready
        viewModel = CurrencyConverterViewModel(
            ioSchedulers,
            mainSchedulers,
            sharedViewModel,
            currencyConversionUseCase,
            manageCurrencyConversionHistoryUseCase
        )
    }

    @Test
    fun `should update currencyList from shared view model`() {
        val expectedCurrencyList = listOf("EUR", "USD", "GBP")

        //relaxed allows return default values and not errors on function like onchange
        val observer: Observer<List<String>> = mockk(relaxed = true)
        viewModel.currencyList.observeForever(observer)

        assertEquals(expectedCurrencyList, viewModel.currencyList.value)
    }

    @Test
    fun `should convert currency and update currencyConversion LiveData`() {
        val fromCurrency = "USD"
        val toCurrency = "EUR"
        val amount = "10.0"
        val convertedAmount = 8.5
        val formattedAmount = "8.50"

        every {
            currencyConversionUseCase(
                fromCurrency,
                toCurrency,
                amount
            )
        } returns Single.just(convertedAmount)

        viewModel.getCurrencyConversion(fromCurrency, toCurrency, amount)

        val observer = mockk<Observer<String>>(relaxed = true)
        viewModel.currencyConversion.observeForever(observer)

        verify { observer.onChanged(formattedAmount) }
        assertEquals(formattedAmount, viewModel.currencyConversion.value)
    }

    @Test
    fun `should handle conversion errors and update errorMessage LiveData`() {
        val fromCurrency = "USD"
        val toCurrency = "EUR"
        val amount = "10.0"
        val error = Throwable("Conversion error")

        every {
            currencyConversionUseCase(
                fromCurrency,
                toCurrency,
                amount
            )
        } returns Single.error(error)

        viewModel.getCurrencyConversion(fromCurrency, toCurrency, amount)

        val observer = mockk<Observer<String>>(relaxed = true)
        viewModel.errorMessage.observeForever(observer)

        verify { observer.onChanged("Error converting currencies") }
        assertEquals("Error converting currencies", viewModel.errorMessage.value)
    }
}
