package com.douglas.financial.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("Total Expenses to be paid: ${state.totalExpensesToBePaid}")
        Text("Total Expenses: ${state.totalExpenses}")

        Button({
            onEvent(HomeContract.Event.DownloadExpenses)
        }) {
            Text("Download Expenses")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeViewPreview() {
    HomeView(
        state = HomeContract.State(totalExpenses = "10", totalExpensesToBePaid = "32"),
        onEvent = {}
    )
}