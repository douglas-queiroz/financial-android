package com.douglas.financial.bi

import com.douglas.financial.data.remote.ExpenseApi
import com.douglas.financial.data.remote.YFinanceApi
import com.douglas.financial.data.remote.buildRetrofit
import com.douglas.financial.data.remote.buildYFinanceRetrofit
import org.koin.dsl.module

val apiModule = module {
    val retrofit = buildRetrofit()
    factory { retrofit.create(ExpenseApi::class.java) }
    val yFinanceRetrofit = buildYFinanceRetrofit()
    factory { yFinanceRetrofit.create(YFinanceApi::class.java) }
}