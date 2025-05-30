package com.douglas.financial.feature.asset.list

class AssetListContract {

    data class UIState(
        val list: List<AssetItem> = emptyList(),
        val editDialogId: String? = null,
        val showEditDialog: Boolean = false,
        val assetToBeDeleted: AssetItem? = null
    )

    sealed class Events {
        data class OnDelete(val asset: AssetItem): Events()
        data class OnEdit(val asset: AssetItem): Events()
        data object OnDismissEditDialog: Events()
        data object AddAsset: Events()
        data class OnDeleteAssetConfirmed(val asset: AssetItem): Events()
        data object OnDeleteDialogClose: Events()
    }
}
