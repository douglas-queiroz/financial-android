package com.douglas.financial.feature.expense.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditExpenseScreen(expenseId: String?, onDismiss: () -> Unit) {
    val viewModel = koinViewModel<EditExpenseViewModel>(parameters = { parametersOf(onDismiss) })

    EditExpenseView(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.setExpense(expenseId)
    }
}