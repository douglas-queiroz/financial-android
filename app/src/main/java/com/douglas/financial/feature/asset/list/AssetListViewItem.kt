package com.douglas.financial.feature.asset.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AssetListViewItem(
    modifier: Modifier = Modifier,
    asset: AssetItem,
    onEvent: (AssetListContract.Events) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = asset.name
            )

            Text(
                textAlign = TextAlign.Center,
                text = asset.total
            )
        }

        Text(
            textAlign = TextAlign.Center,
            text = asset.qtd
        )

        Row {
            IconButton(onClick = { onEvent(AssetListContract.Events.OnEdit(asset)) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = { onEvent(AssetListContract.Events.OnDelete(asset)) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseViewItemPreview() {
    AssetListViewItem (
        asset = AssetItem(
            id = "",
            name = "Internet",
            qtd = "R$ 100.000,00",
            total = "10"
        ),
        onEvent = {},
    )
}
