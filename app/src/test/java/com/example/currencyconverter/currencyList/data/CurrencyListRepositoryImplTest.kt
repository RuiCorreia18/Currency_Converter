package com.example.currencyconverter.currencyList.data

import com.example.currencyconverter.currencyList.data.model.CurrencyRatesResponse
import com.example.currencyconverter.currencyList.domain.CurrencyDomainModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class CurrencyListRepositoryImplTest {


    private val remoteDataSource: RemoteDataSource = mockk()
    private val repository = CurrencyListRepositoryImpl(remoteDataSource)

    @Test
    fun `should return list of CurrencyDomainModel`() {
        val response = CurrencyRatesResponse(
            base = "EUR",
            amount = 1.00,
            rates = mapOf("USD" to 1.20, "GBP" to 1.30)
        )

        val expectedList = listOf(
            CurrencyDomainModel("EUR", 1.00),
            CurrencyDomainModel("USD", 1.20),
            CurrencyDomainModel("GBP", 1.30)
        )

        every { remoteDataSource.getLatestRates() } returns Single.just(response)

        repository.getLatestRates()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue(expectedList)
    }

    @Test
    fun `should handle errors from remoteDataSource`() {
        val error = Throwable("Error fetching data")

        every { remoteDataSource.getLatestRates() } returns Single.error(error)

        repository.getLatestRates()
            .test()
            .assertNotComplete()
            .assertError(error)
    }
}
