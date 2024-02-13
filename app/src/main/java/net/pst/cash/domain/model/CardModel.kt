package net.pst.cash.domain.model

data class CardModel(
    val id: Int? = null,
    val accountId: Int? = null,
    val currencyId: Int? = null,
    val balance: String? = null,
    val lastFourDigits: String? = null
)
