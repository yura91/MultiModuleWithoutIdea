package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class CheckResponseSummary(
    @SerializedName("positive_transactions_sum") var positiveTransactionsSum: String? = null,
    @SerializedName("negative_transactions_sum") var negativeTransactionsSum: String? = null,
    @SerializedName("positive_transactions_count") var positiveTransactionsCount: Int? = null,
    @SerializedName("negative_transactions_count") var negativeTransactionsCount: Int? = null
)