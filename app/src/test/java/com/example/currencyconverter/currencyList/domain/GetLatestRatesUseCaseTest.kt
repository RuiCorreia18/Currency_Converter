package com.example.currencyconverter.currencyList.domain

import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class GetLatestRatesUseCaseTest {
    private val repository: CurrencyListRepository = mockk()
    private val useCase = GetLatestRatesUseCase(repository)

    @Test
    fun `when invoke should return list on success`() {
        val expected = listOf(
            CurrencyDomainModel("EUR", 1.00),
            CurrencyDomainModel("USD", 1.20),
            CurrencyDomainModel("GBP", 1.30),
        )

        every { repository.getLatestRates() } returns Single.just(expected)

        useCase()
            .test()
            .assertNoErrors()
            .assertValue(expected)
    }

    @Test
    fun `when invoke error should return error`() {

        val errorMessage = "Error getting list of currencies"
        val error = Throwable(errorMessage)
        every { repository.getLatestRates() } returns Single.error(error)

        useCase()
            .test()
            .assertNotComplete()
            .assertError(error)
    }
}
