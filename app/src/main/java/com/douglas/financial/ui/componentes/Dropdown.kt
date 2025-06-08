package com.douglas.financial.ui.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    errorMsg: String? = null,
    items: List<String>,
    onSelectItem: (Int) -> Unit,
) {
    var showList by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.clickable { showList = true },
        label = { Text(title) },
        value = value,
        readOnly = true,
        enabled = false,
        onValueChange = {},
        trailingIcon = {
            IconButton(onClick = { showList = true }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select date"
                )
            }
        },
        colors = getOutLinedTextFieldColorsDisableColorsLikeEnable(),
        isError = errorMsg != null,
        supportingText = {
            if (errorMsg != null) {
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )

    if (showList) {
        Dialog(onDismissRequest = {}) {
            Surface(
                shape = MaterialTheme.shapes.large
            ) {
                LazyColumn(modifier = Modifier.padding(16.dp).height(400.dp)) {
                    items(items.size) { index ->
                        TextButton(
                            onClick = {
                                onSelectItem(index)
                                showList = false
                            },
                            content = {
                                Text(
                                    text = items[index],
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .width(200.dp)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}