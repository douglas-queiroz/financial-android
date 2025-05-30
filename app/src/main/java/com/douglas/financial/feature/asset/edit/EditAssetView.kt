package com.douglas.financial.feature.asset.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditAssetView(
    modifier: Modifier,
    state: EditAssetContract.State,
    onEvent: (EditAssetContract.Event) -> Unit
) {
    Surface(shape = MaterialTheme.shapes.large) {

        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Cadastro de Despesa",
                style = MaterialTheme.typography.headlineSmall
            )
            OutlinedTextField(
                label = { Text("Nome") },
                value = state.name,
                onValueChange = {
                    onEvent(EditAssetContract.Event.OnNameChange(it))
                },
                isError = state.nameError != null,
                supportingText = {
                    if (state.nameError != null) {
                        Text(
                            text = state.nameError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End)
            ) {

                OutlinedButton(onClick = {
                    onEvent(EditAssetContract.Event.Cancel)
                }) {
                    Text("Cancelar")
                }

                Button(onClick = {
                    onEvent(EditAssetContract.Event.Save)
                }) {
                    Text("Salvar")
                }
            }
        }
    }
}