package com.douglas.financial.feature.expense.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.douglas.financial.feature.asset.edit.EditAssetScreen
import com.douglas.financial.feature.asset.list.AssetListContract
import com.douglas.financial.feature.asset.list.AssetListScreen
import com.douglas.financial.feature.asset.list.AssetListViewItem
import com.douglas.financial.ui.theme.FinancialTheme
import kotlin.reflect.KFunction1


@Composable
fun AssetListView(
    modifier: Modifier,
    state: AssetListContract.UIState,
    onEvent: (AssetListContract.Events) -> Unit
) {
    Box(modifier.fillMaxSize().padding(16.dp)) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(10.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = "Asset"
                )

                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = "Due Date"
                )

                Text(
                    modifier = Modifier.width(140.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                    text = "Action"
                )
            }

            Column {
                state.list.forEach {
                    AssetListViewItem(
                        asset = it,
                        onEvent = onEvent
                    )
                }
            }
        }

        state.editDialogId?.let {
            Dialog(
                onDismissRequest = { onEvent(AssetListContract.Events.OnDismissEditDialog) }
            ) {
                EditAssetScreen(
                    assetId = state.editDialogId,
                    onDismiss = { onEvent(AssetListContract.Events.OnDismissEditDialog) }
                )
            }
        }

        if (state.assetToBeDeleted != null) {
            AlertDialog(
                title = { Text("Delete") },
                text = { Text("Deseja realmente deletar ${state.assetToBeDeleted.name}?") },
                onDismissRequest = {
                    onEvent(AssetListContract.Events.OnDeleteDialogClose)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onEvent(
                                AssetListContract.Events.OnDeleteAssetConfirmed(asset = state.assetToBeDeleted)
                            )
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onEvent(AssetListContract.Events.OnDeleteDialogClose)
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetListViewPreview() {
    FinancialTheme {
        AssetListView(
            state = AssetListContract.UIState(),
            onEvent = {},
            modifier = Modifier
        )
    }
}