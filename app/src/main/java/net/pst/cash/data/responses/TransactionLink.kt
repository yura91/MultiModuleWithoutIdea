package net.pst.cash.data.responses


import com.google.gson.annotations.SerializedName

data class TransactionLink(
    @SerializedName("active")
    val active: Boolean? = null,
    @SerializedName("label")
    val label: String? = null,
    @SerializedName("url")
    val url: String? = null
)