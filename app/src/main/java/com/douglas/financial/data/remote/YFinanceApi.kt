package com.douglas.financial.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface YFinanceApi {
    @GET("market/v2/get-quotes")
    suspend fun getQuotes(
        @Query("region") region: String = "BR",
        @Query("symbols") symbols: String
    ): YFinanceResponse
}

data class YFinanceResponse (
    var quoteResponse: QuoteResponse
)

data class QuoteResponse(
    var result: List<QuoteResult>
)

data class QuoteResult(
    var symbol: String,
    var regularMarketPrice: Double
)
