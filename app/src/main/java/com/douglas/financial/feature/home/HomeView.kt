package com.douglas.financial.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.douglas.financial.R

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit
) {
    Column(modifier = modifier.padding(20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomeBox(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.home_total_assets_label),
                value = state.totalAssets
            )
            HomeBox(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.home_total_pending_expenses_label),
                value = state.totalExpensesToBePaid
            )
            HomeBox(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.home_total_expenses_label),
                value = state.totalExpenses
            )
        }

        Row {
            Button(onClick = { onEvent(HomeContract.Event.DownloadPriceUpdate) }) {
                Text(text = "Download Price Update")
            }

            Button(onClick = { onEvent(HomeContract.Event.AddCurrencies) }) {
                Text(text = "Add Currencies")
            }
        }

        HomeExpensesTable(
            modifier = Modifier.fillMaxWidth(),
            expenseToBePaid = state.expensesToBePaid,
            onPaidClick = {
                onEvent(HomeContract.Event.MarkExpenseAsPaid(it.id))
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun HomeViewPreview() {
    HomeView(
        state = HomeContract.State(
            totalExpenses = "R$ 320,23",
            totalExpensesToBePaid = "R$ 102,98",
            expensesToBePaid = listOf(
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
        ),
        onEvent = {}
    )
}