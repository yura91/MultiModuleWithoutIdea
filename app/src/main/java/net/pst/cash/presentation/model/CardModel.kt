package net.pst.cash.presentation.model

data class CardModel(
    val id: Int? = null,
    val accountId: Int? = null,
    var currencyType: String? = null,
    var balance: String? = null,
    val holderName: String? = null,
    var lastCardDigits: String? = null,
    var expireDate: String? = null,
    var cvv: String? = null,
    var fullCardNumber: String? = null,
    var rowHistoryItems: MutableList<RowHistoryItems> = mutableListOf()
)
