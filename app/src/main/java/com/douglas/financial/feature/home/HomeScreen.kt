package com.douglas.financial.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, bottomBar: @Composable () -> Unit = {}) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold(
        bottomBar = bottomBar
    ) {
        HomeView(
            modifier = modifier.padding(it),
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}