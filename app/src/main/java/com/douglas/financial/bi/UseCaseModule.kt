package com.douglas.financial.bi

import com.douglas.financial.usecase.DownloadExpensesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        DownloadExpensesUseCase(
            expenseApi = get(),
            expenseDao = get()
        )
    }
}