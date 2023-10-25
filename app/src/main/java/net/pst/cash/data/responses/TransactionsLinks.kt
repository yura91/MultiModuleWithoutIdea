package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName

data class TransactionsLinks(
    @SerializedName("first")
    val first: String? = null,
    @SerializedName("last")
    val last: String? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("prev")
    val prev: Any? = null
)