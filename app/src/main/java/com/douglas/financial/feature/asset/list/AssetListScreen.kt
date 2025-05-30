package com.douglas.financial.feature.asset.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.douglas.financial.feature.expense.list.AssetListView
import org.koin.androidx.compose.koinViewModel

@Composable
fun AssetListScreen(modifier: Modifier = Modifier, bottomBar: @Composable () -> Unit) {
    val viewModel = koinViewModel<AssetListViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AssetListContract.Events.AddAsset)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) {
        AssetListView(
            modifier = modifier.padding(it),
            state = state,
            onEvent = viewModel::onEvent
        )
    }

}
