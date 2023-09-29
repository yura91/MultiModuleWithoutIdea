package net.pst.cash.data

import com.google.gson.annotations.SerializedName

data class VerificationNeedResponse(val data: VerificationData)

data class VerificationData(
    @SerializedName("step")
    val step: String?,
    @SerializedName("remaining_cards")
    val remainingCards: Int?,
    @SerializedName("remaining_deposit")
    val remainingDeposit: Double?
)

