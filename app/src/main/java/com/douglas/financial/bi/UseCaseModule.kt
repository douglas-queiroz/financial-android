package com.douglas.financial.bi

import com.douglas.financial.usecase.AddCurrenciesUseCase
import com.douglas.financial.usecase.DownloadExpensesUseCase
import com.douglas.financial.usecase.MarkExpenseAsPaid
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        DownloadExpensesUseCase(
            expenseApi = get(),
            expenseDao = get()
        )
    }

    factory {
        MarkExpenseAsPaid(
            expensePaymentDao = get(),
            expenseDao = get(),
            dateUtil = get(),
            uuidUtil = get()
        )
    }

    factory {
        AddCurrenciesUseCase(
            currencyDao = get()
        )
    }
}