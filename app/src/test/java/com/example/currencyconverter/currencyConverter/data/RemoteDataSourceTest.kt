package com.example.currencyconverter.currencyConverter.data

import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class RemoteDataSourceTest {
    private val converterApi: CurrencyConvertorApi = mockk()
    private val remoteDataSource = RemoteDataSource(converterApi)

    @Test
    fun `should return converted currency on success`() {
        val fromCurrency = "EUR"
        val toCurrency = "USD"
        val amount = 10.0
        val response = CurrencyConverterResponse(
            amount = amount,
            base = fromCurrency,
            rates = mapOf(toCurrency to 8.5)
        )

        every {
            converterApi.getConvertedCurrency(
                fromCurrency,
                toCurrency,
                amount
            )
        } returns Single.just(response)

        remoteDataSource.getConvertedCurrency(fromCurrency, toCurrency, amount)
            .test()
            .assertNoErrors()
            .assertValue(response)
    }

    @Test
    fun `should return error on failure`() {
        val fromCurrency = "EUR"
        val toCurrency = "USD"
        val amount = 10.0
        val error = Throwable("API error")

        every {
            converterApi.getConvertedCurrency(
                fromCurrency,
                toCurrency,
                amount
            )
        } returns Single.error(error)

        remoteDataSource.getConvertedCurrency(fromCurrency, toCurrency, amount)
            .test()
            .assertError(error)
            .assertNoValues()
    }
}
