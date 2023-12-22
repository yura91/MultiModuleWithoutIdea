package net.pst.cash.data.requests

import com.google.gson.annotations.SerializedName

data class IssueCardRequest(
    @SerializedName("account_id")
    val accountId: Int?,
    @SerializedName("type")
    val type: String? = "sigma_no_limits",
    @SerializedName("start_balance")
    val startBalance: String?,
    @SerializedName("with_error_data")
    val withErrorData: Boolean = true,
)
