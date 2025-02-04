package com.douglas.financial.data.remote

import com.douglas.financial.model.Expense
import retrofit2.http.GET

interface ExpenseApi {

    @GET("expense")
    suspend fun getAll(): List<Expense>
}