package net.pst.cash.domain.model

data class RowHistoryItems(var date: String, val elements: MutableList<HistoryItem>)
