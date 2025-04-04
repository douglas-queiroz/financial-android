package com.douglas.financial.usecase

import com.douglas.financial.data.local.CurrencyDao
import com.douglas.financial.model.Currency
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AddCurrenciesUseCase(private val currencyDao: CurrencyDao) {

    @OptIn(ExperimentalUuidApi::class)
    suspend operator fun invoke() {
        val brl = Currency(
            id = Uuid.random().toHexString(),
            name = "Real",
            code = "",
            symbol = "R$",
            exchangeRate = 1.0,
            isBase = true
        )
        val usd = Currency(
            id = Uuid.random().toHexString(),
            name = "Dólar",
            code = "BRL=X",
            symbol = "$",
            exchangeRate = 5.69,
            isBase = false
        )

        val currencies = listOf(brl, usd)

        currencyDao.insertAll(currencies)
    }
}