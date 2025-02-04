package com.douglas.financial.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    onEvent: (HomeContract.Event) -> Unit) {
    Column(modifier = modifier.fillMaxSize()) {
        Button({
            onEvent(HomeContract.Event.DownloadExpenses)
        }) {
            Text("Download Expenses")
        }
    }

}