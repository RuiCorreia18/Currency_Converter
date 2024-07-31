package com.example.currencyconverter.currencyList.data

import com.example.currencyconverter.currencyList.data.model.CurrencyRatesResponse
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class RemoteDataSourceTest {
    private val currencyListApi: CurrencyListApi = mockk()

    private val remoteDataSource = RemoteDataSource(currencyListApi)

    @Test
    fun `getLatestRates should return rates on success`() {
        val response = CurrencyRatesResponse(
            base = "EUR",
            amount = 1.00,
            rates = mapOf("USD" to 1.12)
        )
        every { currencyListApi.getLatestRates() } returns Single.just(response)

        remoteDataSource.getLatestRates()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue(response)
    }

    @Test
    fun `getLatestRates should return error on failure`() {
        val error = Throwable("Network error")
        every { currencyListApi.getLatestRates() } returns Single.error(error)

        remoteDataSource.getLatestRates()
            .test()
            .assertNotComplete()
            .assertError(error)
    }
}
