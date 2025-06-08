package com.douglas.financial.feature.asset.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditAssetScreen(
    modifier: Modifier = Modifier,
    assetId: String?,
    onDismiss: () -> Unit
) {
    val viewModel = koinViewModel<EditAssetViewModel>(parameters = { parametersOf(onDismiss) })
    val state by viewModel.state.collectAsState()
    EditAssetView(
        modifier = modifier,
        state = state,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.setAsset(assetId)
    }
}
