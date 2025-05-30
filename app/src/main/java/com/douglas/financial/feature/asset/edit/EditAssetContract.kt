package com.douglas.financial.feature.asset.edit

class EditAssetContract {
    data class State(
        val name: String = "",
        val nameError: String? = null,
        val qtd: String = "",
        val qtdError: String? = null,
        val value: String = "",
        val valueError: String? = null
    )

    sealed class Event {
        data class OnNameChange(val name: String): Event()
        data class OnQtdChange(val qtd: String): Event()
        data class OnValueChange(val value: String): Event()
        data object Save: Event()
        data object Cancel: Event()
    }

}
