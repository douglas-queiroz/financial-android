package com.douglas.financial.feature.expense.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EditExpenseView(
    modifier: Modifier = Modifier,
    state: EditExpenseContract.State,
    onEvent: (EditExpenseContract.Event) -> Unit
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
                    onEvent(EditExpenseContract.Event.OnNameChange(it))
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
                label = { Text("Valor") },
                value = state.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = {
                    onEvent(EditExpenseContract.Event.OnValueChange(it))
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

            OutlinedTextField(
                label = { Text("Vencimento") },
                value = state.dueDate,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    onEvent(EditExpenseContract.Event.OnDueDateChange(it))
                },
                isError = state.dueDateError != null,
                supportingText = {
                    if (state.dueDateError != null) {
                        Text(
                            text = state.dueDateError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End)
            ) {

                OutlinedButton(onClick = {
                    onEvent(EditExpenseContract.Event.Cancel)
                }) {
                    Text("Cancelar")
                }

                Button(onClick = {
                    onEvent(EditExpenseContract.Event.Save)
                }) {
                    Text("Salvar")
                }
            }
        }
    }
}

@Preview
@Composable
fun EditExpenseViewPreview() {
    EditExpenseView(
        state = EditExpenseContract.State(),
        onEvent = {}
    )
}