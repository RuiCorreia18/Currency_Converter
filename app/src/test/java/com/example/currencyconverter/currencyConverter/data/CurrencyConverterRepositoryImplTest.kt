package com.example.currencyconverter.currencyConverter.data

import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class CurrencyConverterRepositoryImplTest {
    private val remoteDataSource: RemoteDataSource = mockk()
    private val repository = CurrencyConverterRepositoryImpl(remoteDataSource)

    @Test
    fun `should return correct conversion rate when API returns valid response`() {
        val fromCurrency = "USD"
        val toCurrency = "EUR"
        val amount = 10.0
        val conversionRate = 8.5
        val response = CurrencyConverterResponse(
            amount = amount,
            base = fromCurrency,
            rates = mapOf(toCurrency to conversionRate)
        )

        every {
            remoteDataSource.getConvertedCurrency(
                fromCurrency,
                toCurrency,
                amount
            )
        } returns Single.just(response)

        repository.getCurrencyConversion(fromCurrency, toCurrency, amount)
            .test()
            .assertNoErrors()
            .assertValue(conversionRate)
    }

    @Test
    fun `should return 0 when API response does not contain target currency rate`() {
        val fromCurrency = "USD"
        val toCurrency = "EUR"
        val amount = 10.0
        val response = CurrencyConverterResponse(
            amount = amount,
            base = fromCurrency,
            rates = emptyMap()
        )

        every {
            remoteDataSource.getConvertedCurrency(
                fromCurrency,
                toCurrency,
                amount
            )
        } returns Single.just(response)

        repository.getCurrencyConversion(fromCurrency, toCurrency, amount)
            .test()
            .assertNoErrors()
            .assertValue(0.0)
    }

    @Test
    fun `should propagate error when API returns error`() {
        val fromCurrency = "EUR"
        val toCurrency = "USD"
        val amount = 10.0
        val error = Throwable("API error")

        every {
            remoteDataSource.getConvertedCurrency(
                fromCurrency,
                toCurrency,
                amount
            )
        } returns Single.error(error)

        repository.getCurrencyConversion(fromCurrency, toCurrency, amount)
            .test()
            .assertNoValues()
            .assertError(error)
    }
}
