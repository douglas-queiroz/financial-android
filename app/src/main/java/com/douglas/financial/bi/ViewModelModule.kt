package com.douglas.financial.bi

import com.douglas.financial.feature.expense.edit.EditExpenseViewModel
import com.douglas.financial.feature.expense.list.ExpenseViewModel
import com.douglas.financial.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { ExpenseViewModel(get(), get(), get()) }
    viewModel { params -> EditExpenseViewModel(get(), params.get()) }
}