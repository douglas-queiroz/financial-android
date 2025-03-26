package com.douglas.financial.feature.expense.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExpensesScreen(modifier: Modifier = Modifier, bottomBar: @Composable () -> Unit) {
    val viewModel = koinViewModel<ExpenseViewModel>()
    val state by viewModel.state.collectAsState()

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
            state = state,
            onEvent = viewModel::onEvent,
        )
    }
}