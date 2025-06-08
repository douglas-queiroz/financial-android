package com.douglas.financial.feature.asset.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.data.local.AssetDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AssetListViewModel(
    private val assetDao: AssetDao
): ViewModel() {

    private val _state = MutableStateFlow(AssetListContract.UIState())
    val state: StateFlow<AssetListContract.UIState> = _state.onStart {
        loadAssets()
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Lazily,
        initialValue = _state.value
    )

    private fun loadAssets() {
        viewModelScope.launch {
            assetDao.getAll().collect { assets ->
                val list = assets.map {
                    val total = it.qtd * it.value
                    AssetItem(
                        id = it.id,
                        name = it.name,
                        qtd = it.qtd.toString(),
                        total = total.toString()
                    )
                }

                _state.update {
                    it.copy(list = list)
                }
            }
        }
    }

    fun onEvent(event: AssetListContract.Events) {
        when(event) {
            is AssetListContract.Events.OnDelete -> {
                _state.update { it.copy(assetToBeDeleted = event.asset) }
            }
            is AssetListContract.Events.OnDismissEditDialog -> {
                _state.update { it.copy(
                    showEditDialog = false,
                    editDialogId = null)
                }
            }
            is AssetListContract.Events.OnEdit -> {
                _state.update { it.copy(
                    showEditDialog = true,
                    editDialogId = event.asset.id)
                }
            }
            is AssetListContract.Events.AddAsset -> {
                _state.update { it.copy(showEditDialog = true, editDialogId = null) }
            }
            is AssetListContract.Events.OnDeleteAssetConfirmed -> {
                deleteAsset(event)
            }
            is AssetListContract.Events.OnDeleteDialogClose -> {
                _state.update { it.copy(assetToBeDeleted = null) }
            }
        }
    }

    private fun deleteAsset(
        event: AssetListContract.Events.OnDeleteAssetConfirmed
    ) = viewModelScope.launch {
        val assert = assetDao.getById(event.asset.id)
        assetDao.delete(assert)
    }
}