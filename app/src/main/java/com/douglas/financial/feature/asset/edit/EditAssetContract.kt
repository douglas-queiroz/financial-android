package com.douglas.financial.feature.asset.edit

class EditAssetContract {
    data class State(
        val name: String = "",
        val nameError: String? = null,
        val code: String = "",
        val codeError: String? = null,
        val qtd: String = "",
        val qtdError: String? = null,
        val value: String = "",
        val valueError: String? = null,
        val currency: String = "",
        val currencyError: String? = null,
        val currencyList: List<String> = emptyList(),
        val assetType: String = "",
        val assetTypeError: String? = null,
        val assetTypeList: List<String> = emptyList()
    )

    sealed class Event {
        data class OnNameChange(val name: String): Event()
        data class OnCodeChange(val code: String): Event()
        data class OnQtdChange(val qtd: String): Event()
        data class OnValueChange(val value: String): Event()
        data class OnSelectCurrency(val currencyIndex: Int): Event()
        data class OnSelectAssetType(val assetTypeIndex: Int): Event()
        data object Save: Event()
        data object Cancel: Event()
    }

}
