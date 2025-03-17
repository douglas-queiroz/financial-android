package com.douglas.financial.feature.expense.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.douglas.financial.feature.expense.edit.EditExpenseScreen

@Composable
fun ExpenseView(
    modifier: Modifier = Modifier,
    state: ExpenseContract.State,
    onEvent: (ExpenseContract.Events) -> Unit
) {
    Box(modifier.fillMaxSize().padding(16.dp)) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(10.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = "Expenses"
                )

                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = "Due Date"
                )

                Text(
                    modifier = Modifier.width(140.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                    text = "Action"
                )
            }

            Column {
                state.expenses.forEach {
                    ExpenseViewItem(
                        expense = it,
                        onEvent = onEvent
                    )
                }
            }
        }

        if (state.showEditDialog) {
            Dialog(
                onDismissRequest = { onEvent(ExpenseContract.Events.OnDismiss) }
            ) {
                EditExpenseScreen(
                    expenseId = state.editExpenseId,
                    onDismiss = { onEvent(ExpenseContract.Events.OnDismiss) }
                )
            }
        }

        if (state.expenseToBeDeleted != null) {
            AlertDialog(
                title = { Text("Delete") },
                text = { Text("Deseja realmente deletar ${state.expenseToBeDeleted.name}?") },
                onDismissRequest = {
                    onEvent(ExpenseContract.Events.OnDeleteDialogClose)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onEvent(
                                ExpenseContract.Events.OnDeleteExpenseConfirmed(expense = state.expenseToBeDeleted)
                            )
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onEvent(ExpenseContract.Events.OnDeleteDialogClose)
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ExpenseViewPreview() {
    ExpenseView(
        state = ExpenseContract.State(
            expenses = listOf(
                ExpenseItem(
                    id = "",
                    name = "Internet",
                    value = "R$ 100.000,00",
                    dueDate = "10",
                    paymentId = null
                ),
                ExpenseItem(
                    id = "",
                    name = "Internet",
                    value = "R$ 100.000,00",
                    dueDate = "10",
                    paymentId = "1231231"
                ),
                ExpenseItem(
                    id = "",
                    name = "Internet",
                    value = "R$ 100.000,00",
                    dueDate = "10",
                    paymentId = null
                ),
                ExpenseItem(
                    id = "",
                    name = "Internet",
                    value = "R$ 100.000,00",
                    dueDate = "10",
                    paymentId = null
                )
            )
        ),
        onEvent = {}
    )
}