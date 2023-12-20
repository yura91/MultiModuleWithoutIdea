package net.pst.cash.presentation.model

data class SelectedBalanceModel(
    val balanceItemModels: List<BalanceItemModel>?,
    val account: String?
)
