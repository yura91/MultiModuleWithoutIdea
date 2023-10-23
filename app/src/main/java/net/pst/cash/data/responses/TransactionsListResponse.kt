package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class TransactionsListResponse(
    @SerializedName("data") var data: ArrayList<TransactionsListData> = arrayListOf()
)