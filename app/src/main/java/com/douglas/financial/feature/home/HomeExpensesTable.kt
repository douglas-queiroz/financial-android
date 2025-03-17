package com.douglas.financial.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeExpensesTable(
    modifier: Modifier = Modifier,
    expenseToBePaid: List<HomeContract.ExpenseToBePaid>,
    onPaidClick: (HomeContract.ExpenseToBePaid) -> Unit,
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer).padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            text = "Expenses to be paid"
        )
        Column(modifier = modifier.background(MaterialTheme.colorScheme.onPrimary)) {
            expenseToBePaid.forEach { expense ->
                HomeExpensesTableItem(
                    expenseToBePaid = HomeContract.ExpenseToBePaid(
                        id = expense.id,
                        description = expense.description,
                        date = expense.date,
                        value = expense.value
                    ),
                    onPaidClick = onPaidClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeExpensesTablePreview() {
    HomeExpensesTable(
        expenseToBePaid = listOf(
            HomeContract.ExpenseToBePaid(
                id = "",
                description = "Internet",
                date = "10/05/2023",
                value = "R$ 99,90"
            ),
            HomeContract.ExpenseToBePaid(
                id = "",
                description = "Internet",
                date = "10/05/2023",
                value = "R$ 99,90"
            ),
        ),
        onPaidClick = {}
    )
}