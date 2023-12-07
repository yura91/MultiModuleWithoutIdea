package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName

data class ConfigResponse(
    @SerializedName("register_hash") val registerHash: String? = null,
    @SerializedName("tariffs") val tariffs: ArrayList<Tariff> = arrayListOf()
)

data class Tariff(
    @SerializedName("price") val price: Int? = null,
    @SerializedName("balance") val balance: Int? = null
)
