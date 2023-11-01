package net.pst.cash.domain.model

data class TransactionModel(
    var id: Int = 0,
    var sum: String = "",
    var description: String = "",
    var datePart: String = "",
    var timePart: String = ""
)
