package net.pst.cash.domain.model

data class ConfigData(
    val registerHash: String? = null,
    val tariffs: ArrayList<Tariff> = arrayListOf()
)

data class Tariff(
    val price: Int? = null,
    val balance: Int? = null
)
