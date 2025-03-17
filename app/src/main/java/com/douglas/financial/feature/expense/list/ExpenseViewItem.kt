package com.douglas.financial.feature.expense.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseViewItem(
    modifier: Modifier = Modifier,
    expense: ExpenseItem,
    onEvent: (ExpenseContract.Events) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = expense.name
            )

            Text(
                textAlign = TextAlign.Center,
                text = expense.value
            )
        }

        Text(
            textAlign = TextAlign.Center,
            text = expense.dueDate
        )

        Row {
            if (expense.paymentId == null) {
                IconButton(onClick = { onEvent(ExpenseContract.Events.OnMarkPaid(expense)) }) {
                    Icon(Icons.Default.Check, contentDescription = "Paid")
                }
            } else {
                IconButton(onClick = { onEvent(ExpenseContract.Events.OnMarkUnpaid(expense)) }) {
                    Icon(Icons.Default.Close, contentDescription = "Paid")
                }
            }
            IconButton(onClick = { onEvent(ExpenseContract.Events.Edit(expense)) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = { onEvent(ExpenseContract.Events.OnDelete(expense)) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseViewItemPreview() {
    ExpenseViewItem(
        expense = ExpenseItem(
            id = "",
            name = "Internet",
            value = "R$ 100.000,00",
            dueDate = "10",
            paymentId = null
        ),
        onEvent = {},
    )
}