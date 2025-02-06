package com.douglas.financial.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
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
    onEvent: (HomeContract.Event) -> Unit,
) {
    Scaffold { padding ->
        Column(modifier = modifier.padding(padding)) {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
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

            Button({
                onEvent(HomeContract.Event.DownloadExpenses)
            }) {
                Text("Download Expenses")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeViewPreview() {
    HomeView(
        state = HomeContract.State(
            totalExpenses = "R$ 320,23",
            totalExpensesToBePaid = "R$ 102,98"
        ),
        onEvent = {}
    )
}