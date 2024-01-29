package net.pst.cash.presentation.model

data class CardModel(
    val id: Int? = null,
    val currencyType: String? = null,
    val balance: String? = null,
    val holderName: String? = null,
    val lastCardDigits: String? = null,
    val expireDate: String? = null,
    var rowHistoryItems: List<RowHistoryItems> = listOf()
)
