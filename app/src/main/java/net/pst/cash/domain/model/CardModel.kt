package net.pst.cash.domain.model

data class CardModel(
    val id: Int? = null,
    val currencyId: Int? = null,
    val balance: String? = null,
    val holderName: String? = null,
    val lastCardDigits: String? = null,
    val expireDate: String? = null
)
