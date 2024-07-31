package com.example.currencyconverter.currencyList.ui

import com.example.currencyconverter.currencyList.domain.CurrencyDomainModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyMapperTest {

    @Test
    fun `should correctly convert CurrencyDomainModel to CurrencyUIModel`() {
        val domainModel = CurrencyDomainModel(
            code = "USD",
            rate = 1.10145
        )

        val expectedRate = "1.10"
        val expectedSymbol = "$"

        val uiModel = domainModel.toUIModel()

        assertEquals(domainModel.code, uiModel.code)
        assertEquals(expectedRate, uiModel.rate)
        assertEquals(expectedSymbol, uiModel.symbol)
    }
}
