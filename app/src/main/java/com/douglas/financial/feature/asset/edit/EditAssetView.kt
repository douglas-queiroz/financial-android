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
import com.douglas.financial.ui.componentes.Dropdown

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
                text = "Cadastro de Ativo",
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
            OutlinedTextField(
                label = { Text("Code") },
                value = state.code,
                onValueChange = {
                    onEvent(EditAssetContract.Event.OnCodeChange(it))
                },
                isError = state.codeError != null,
                supportingText = {
                    if (state.codeError != null) {
                        Text(
                            text = state.codeError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            OutlinedTextField(
                label = { Text("Qtd") },
                value = state.qtd,
                onValueChange = {
                    onEvent(EditAssetContract.Event.OnQtdChange(it))
                },
                isError = state.qtdError != null,
                supportingText = {
                    if (state.qtdError != null) {
                        Text(
                            text = state.qtdError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                label = { Text("Valor") },
                value = state.value,
                onValueChange = {
                    onEvent(EditAssetContract.Event.OnValueChange(it))
                },
                isError = state.valueError != null,
                supportingText = {
                    if (state.valueError != null) {
                        Text(
                            text = state.valueError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Dropdown(
                title = "Currency",
                value = state.currency,
                items = state.currencyList,
                errorMsg = state.currencyError,
                onSelectItem = {
                    onEvent(EditAssetContract.Event.OnSelectCurrency(it))
                }
            )

            Dropdown(
                title = "Asset Type",
                value = state.assetType,
                items = state.assetTypeList,
                errorMsg = state.assetTypeError,
                onSelectItem = {
                    onEvent(EditAssetContract.Event.OnSelectAssetType(it))
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