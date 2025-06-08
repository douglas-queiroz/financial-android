package com.douglas.financial.feature.asset.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.data.local.AssetDao
import com.douglas.financial.data.local.CurrencyDao
import com.douglas.financial.model.Asset
import com.douglas.financial.model.AssetType
import com.douglas.financial.model.Currency
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditAssetViewModel(
    private val currencyDao: CurrencyDao,
    private val assetDao: AssetDao,
    private val onDismiss: () -> Unit
): ViewModel() {
    private lateinit var asset: Asset
    private lateinit var currencies: List<Currency>
    private var _state = MutableStateFlow(EditAssetContract.State())
    val state: StateFlow<EditAssetContract.State> = _state.onStart {
        loadCurrencies()
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Lazily,
        initialValue = _state.value
    )

    private fun loadCurrencies() = viewModelScope.launch {
        viewModelScope.launch {
            currencies = currencyDao.getAll()
        }
    }

    fun onEvent(event: EditAssetContract.Event) {
        when(event) {
            is EditAssetContract.Event.OnNameChange -> {
                _state.update {
                    it.copy(name = event.name)
                }
                asset = asset.copy(name = event.name)
            }
            is EditAssetContract.Event.OnCodeChange -> {
                _state.update {
                    it.copy(code = event.code)
                }
                asset = asset.copy(code = event.code)
            }
            is EditAssetContract.Event.OnQtdChange -> {
                _state.update {
                    it.copy(qtd = event.qtd)
                }
                val qtd = if (event.qtd.isEmpty()) {
                    0.0
                } else {
                    event.qtd.toDouble()
                }
                asset = asset.copy(qtd = qtd)
            }
            is EditAssetContract.Event.OnValueChange -> {
                _state.update {
                    it.copy(value = event.value)
                }
                val value = if (event.value.isEmpty()) {
                    0.0
                } else {
                    event.value.toDouble()
                }
                asset = asset.copy(value = value)
            }
            is EditAssetContract.Event.OnSelectCurrency -> {
                _state.update {
                    it.copy(currency = currencies[event.currencyIndex].name)
                }
                asset = asset.copy(currencyId = currencies[event.currencyIndex].id)
            }
            is EditAssetContract.Event.OnSelectAssetType -> {
                _state.update {
                    it.copy(
                        assetType = AssetType.entries[event.assetTypeIndex].name
                    )
                }
                asset = asset.copy(type = AssetType.entries[event.assetTypeIndex])
            }
            is EditAssetContract.Event.Save -> save()
            is EditAssetContract.Event.Cancel -> onDismiss()
        }
    }

    fun setAsset(assetId: String?) = viewModelScope.launch {
        asset = if (assetId == null) {
            Asset(
                id = "",
                code = "",
                name = "",
                qtd = 0.0,
                value = 0.0,
                currencyId = "",
                type = AssetType.STOCK
            )
        } else {
            assetDao.getById(assetId)
        }

        val currency = currencies.find { it.id == asset.currencyId }?.name ?: ""
        val type = AssetType.entries.find { it == asset.type }?.name ?: ""

        _state.update {
            it.copy(
                name = asset.name,
                code = asset.code,
                qtd = asset.qtd.toString(),
                value = asset.value.toString(),
                currency = currency,
                currencyList = currencies.map { it.name },
                assetType = type,
                assetTypeList = AssetType.entries.map { it.name }
            )
        }
    }

    private fun save() = viewModelScope.launch {
        withContext(IO) {

            if (asset.id.isEmpty()) {
                assetDao.insert(asset = asset)
            } else {
                assetDao.update(asset = asset)
            }

            onDismiss()
        }
    }
}