package com.douglas.financial.bi

import com.douglas.financial.data.remote.ExpenseApi
import com.douglas.financial.data.remote.buildRetrofit
import org.koin.dsl.module

val apiModule = module {
    val retrofit = buildRetrofit()
    factory { retrofit.create(ExpenseApi::class.java) }
}