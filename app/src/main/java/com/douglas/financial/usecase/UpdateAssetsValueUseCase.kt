package com.douglas.financial.usecase

import com.douglas.financial.data.local.AssetDao
import com.douglas.financial.data.local.AssetTrackDao
import com.douglas.financial.data.local.CurrencyDao
import com.douglas.financial.data.local.CurrencyExchangeRateTrackDao
import com.douglas.financial.data.remote.YFinanceApi
import com.douglas.financial.model.AssetTrack
import com.douglas.financial.model.CurrencyExchangeRateTrack
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID

class UpdateAssetsValueUseCase(
    private val assetDao: AssetDao,
    private val assetTrackDao: AssetTrackDao,
    private val currencyDao: CurrencyDao,
    private val currencyExchangeRateTrackDao: CurrencyExchangeRateTrackDao,
    private val yFinanceApi: YFinanceApi
) {

    suspend operator fun invoke() = withContext(IO) {
        val baseCurrency = currencyDao.getBaseCurrency()
        checkNotNull(baseCurrency)

        val assets = assetDao.getAll()
        val currencies = currencyDao.getAll()

        val assetsCode = assets
            .filter { it.code.isNotEmpty() }
            .map { it.code }

        val currenciesCode = currencies
            .filter { it.code.isNotEmpty() }
            .map { it.code }


        val symbols = assetsCode + currenciesCode
        val response = yFinanceApi.getQuotes(symbols = symbols.joinToString(","))

        response.quoteResponse.result.forEach {
            val asset = assets.find { asset -> asset.code == it.symbol }
            val currency = currencies.find { currency -> currency.code == it.symbol }
            if (asset != null) {
                val updatedAsset = asset.copy(value = it.regularMarketPrice)
                assetDao.update(updatedAsset)
                val  assetTrack = AssetTrack(
                    id = UUID.randomUUID().toString(),
                    assetId = asset.id,
                    value = it.regularMarketPrice,
                    date = LocalDateTime.now()
                )
                assetTrackDao.insert(assetTrack)
            } else if (currency != null) {
                val updatedCurrency = currency.copy(exchangeRate = it.regularMarketPrice)
                currencyDao.update(updatedCurrency)
                val currencyExchangeRateTrack = CurrencyExchangeRateTrack(
                    id = UUID.randomUUID().toString(),
                    currencyIdTo = baseCurrency.id,
                    currencyIdFrom = currency.id,
                    exchangeRate = it.regularMarketPrice,
                    date = LocalDateTime.now()
                )
                currencyExchangeRateTrackDao.insert(currencyExchangeRateTrack)
            }
        }
    }
}