package com.douglas.financial.feature.expense.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExpensesScreen(modifier: Modifier = Modifier, bottomBar: @Composable () -> Unit) {
    val viewModel = koinViewModel<ExpenseViewModel>()

    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(ExpenseContract.Events.AddExpense)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) {
        ExpenseView(
            modifier = modifier.padding(it),
            state = viewModel.state,
            onEvent = viewModel::onEvent,
        )
    }
}