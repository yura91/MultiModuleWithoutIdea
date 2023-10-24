package net.pst.cash.presentation.model

data class HistoryPaymentModel(
    val title: String,
    val historyItems: MutableList<HistoryItem> = mutableListOf()
)

data class HistoryItem(
    val operationType: String,
    val operationValue: String,
    val operationTime: String
)