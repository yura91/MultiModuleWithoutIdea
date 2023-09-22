package com.example.multimoduleapp.presentation.model

data class HistoryPaymentModel(val title: String, val historyItems: List<HistoryItem>)

data class HistoryItem(
    val operationType: String,
    val operationValue: String,
    val operationTime: String
)