package net.pst.cash.data.responses


import com.google.gson.annotations.SerializedName

data class TransactionMeta(
    @SerializedName("current_page")
    val currentPage: Int? = null,
    @SerializedName("from")
    val from: Int? = null,
    @SerializedName("last_page")
    val lastPage: Int? = null,
    @SerializedName("links")
    val links: List<TransactionLink?>? = null,
    @SerializedName("path")
    val path: String? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("to")
    val to: Int? = null,
    @SerializedName("total")
    val total: Int? = null
)