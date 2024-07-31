package com.example.currencyconverter.currencyConverter.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.currencyconverter.currencyConverter.domain.GetCurrencyConversionUseCase
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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CurrencyConverterViewModelTest {
    private val currencyConversionUseCase: GetCurrencyConversionUseCase = mockk()
    private val ioSchedulers: Scheduler = Schedulers.trampoline()
    private val mainSchedulers: Scheduler = Schedulers.trampoline()
    private val sharedViewModel: SharedViewModel = mockk(relaxed = true)

    private val currencyList = listOf("EUR", "USD", "GBP")

    private val viewModel = CurrencyConverterViewModel(
        ioSchedulers,
        mainSchedulers,
        sharedViewModel,
        currencyConversionUseCase
    )

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        sharedViewModel.setCurrencyListLiveData(currencyList)
    }

    //TODO fix init test
    /*@Test
    fun `should update currencyList from shared view model`() {

        viewModel

        assertEquals(currencyList, viewModel.currencyList.value)
    }*/

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
                amount.toDouble()
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
                amount.toDouble()
            )
        } returns Single.error(error)

        viewModel.getCurrencyConversion(fromCurrency, toCurrency, amount)

        val observer = mockk<Observer<String>>(relaxed = true)
        viewModel.errorMessage.observeForever(observer)
        verify { observer.onChanged("Error converting currencies") }
        assertEquals("Error converting currencies", viewModel.errorMessage.value)
    }

}
