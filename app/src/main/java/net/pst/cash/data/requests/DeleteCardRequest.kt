package net.pst.cash.data.requests

import com.google.gson.annotations.SerializedName

data class DeleteCardRequest(
    @SerializedName("account_id")
    val accountId: Int?
)
