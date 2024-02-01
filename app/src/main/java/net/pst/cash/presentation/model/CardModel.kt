package net.pst.cash.presentation.model

data class CardModel(
    val id: Int? = null,
    val currencyType: String? = null,
    val balance: String? = null,
    val holderName: String? = null,
    val lastCardDigits: String? = null,
    var expireDate: String? = null,
    var cvv: String? = null,
    var fullCardNumber: String? = null,
    var rowHistoryItems: MutableList<RowHistoryItems> = mutableListOf()
)
