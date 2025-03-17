package com.douglas.financial.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, bottomBar: @Composable () -> Unit = {}) {
    val viewModel = koinViewModel<HomeViewModel>()

    Scaffold(
        bottomBar = bottomBar
    ) {
        HomeView(
            modifier = modifier.padding(it),
            state = viewModel.state.collectAsState().value,
            onEvent = viewModel::onEvent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}