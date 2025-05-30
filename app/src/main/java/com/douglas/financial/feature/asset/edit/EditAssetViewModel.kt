package com.douglas.financial.feature.asset.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.data.local.AssetDao
import com.douglas.financial.model.Asset
import com.douglas.financial.model.AssetType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditAssetViewModel(
    private val assetDao: AssetDao,
    private val onDismiss: () -> Unit
): ViewModel() {
    private lateinit var asset: Asset
    private var _state = MutableStateFlow(EditAssetContract.State())
    val state: StateFlow<EditAssetContract.State> = _state.asStateFlow()

    fun onEvent(event: EditAssetContract.Event) {
        when(event) {
            is EditAssetContract.Event.OnNameChange -> {
                _state.update {
                    it.copy(name = event.name)
                }
            }
            is EditAssetContract.Event.OnQtdChange -> {
                _state.update {
                    it.copy(qtd = event.qtd)
                }
            }
            is EditAssetContract.Event.OnValueChange -> {
                _state.update {
                    it.copy(value = event.value)
                }
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

        _state.update {
            it.copy(
                name = asset.name,
                qtd = asset.qtd.toString(),
                value = asset.value.toString(),
            )
        }
    }

    private fun save() {
        TODO("Not yet implemented")
    }
}