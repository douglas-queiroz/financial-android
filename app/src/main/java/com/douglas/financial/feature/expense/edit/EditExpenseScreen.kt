package com.douglas.financial.feature.expense.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditExpenseScreen(expenseId: String?, onDismiss: () -> Unit) {
    val viewModel = koinViewModel<EditExpenseViewModel>(parameters = { parametersOf(onDismiss) })
    val state by viewModel.state.collectAsState()

    EditExpenseView(
        state = state,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.setExpense(expenseId)
    }
}