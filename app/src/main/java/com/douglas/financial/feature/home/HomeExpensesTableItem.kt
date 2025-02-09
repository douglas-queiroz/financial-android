package com.douglas.financial.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeExpensesTableItem(
    modifier: Modifier = Modifier,
    expenseToBePaid: HomeContract.ExpensesToBePaid,
    onPaidClick: (HomeContract.ExpensesToBePaid) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = expenseToBePaid.description,
                style = MaterialTheme.typography.bodySmall
            )
            Row {
                Text(
                    text = expenseToBePaid.date,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Text(
            text = expenseToBePaid.value,
            style = MaterialTheme.typography.bodyLarge
        )

        Button({ onPaidClick(expenseToBePaid) }) {
            Text("Paid")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeExpensesTableItemPreview() {
    HomeExpensesTableItem(
        expenseToBePaid = HomeContract.ExpensesToBePaid(
            id = "",
            description = "Internet",
            date = "10/05/2023",
            value = "R$ 99,90"
        ),
        onPaidClick = {}
    )
}