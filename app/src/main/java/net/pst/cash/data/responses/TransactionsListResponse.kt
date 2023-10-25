package net.pst.cash.data.responses


import com.google.gson.annotations.SerializedName

data class TransactionsListResponse(
    @SerializedName("data")
    val `data`: List<TransactionsListData>? = listOf(),
    @SerializedName("links")
    val links: TransactionsLinks? = TransactionsLinks(),
    @SerializedName("meta")
    val meta: TransactionMeta? = TransactionMeta()
)