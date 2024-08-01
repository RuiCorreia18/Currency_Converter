package com.example.currencyconverter.currencyConverter.domain

import com.example.currencyconverter.currencyConverter.domain.useCases.GetCurrencyConversionUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class GetCurrencyConversionUseCaseTest {
    private val repository: CurrencyConverterRepository = mockk()
    private val useCase = GetCurrencyConversionUseCase(repository)

    @Test
    fun `when invoke should convert`() {
        val fromCurrency = "USD"
        val toCurrency = "EUR"
        val amount = 10.0
        val convertedAmount = 8.5

        every {
            repository.getCurrencyConversion(
                fromCurrency,
                toCurrency,
                amount
            )
        } returns Single.just(convertedAmount)

        useCase(fromCurrency, toCurrency, amount.toString())
            .test()
            .assertNoErrors()
            .assertValue(convertedAmount)
    }

    @Test
    fun `when invoke error should return error`() {
        val errorMessage = "Error getting list of currencies"
        val error = Throwable(errorMessage)

        val fromCurrency = "USD"
        val toCurrency = "EUR"
        val amount = 10.0

        every {
            repository.getCurrencyConversion(
                fromCurrency,
                toCurrency,
                amount
            )
        } returns Single.error(error)

        useCase(fromCurrency, toCurrency, amount.toString())
            .test()
            .assertNotComplete()
            .assertError(error)
    }
}
