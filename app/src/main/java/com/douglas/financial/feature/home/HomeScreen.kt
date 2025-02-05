package com.douglas.financial.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<HomeViewModel>()

    HomeView(
        modifier = modifier,
        state = viewModel.state.collectAsState().value,
        onEvent = viewModel::onEvent,
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}