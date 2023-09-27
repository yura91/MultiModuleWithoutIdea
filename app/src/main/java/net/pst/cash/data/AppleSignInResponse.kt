package net.pst.cash.data

import com.google.gson.annotations.SerializedName

data class AppleSignInResponse(val data: AppleTokenData)
data class AppleTokenData(
    @SerializedName("token")
    val token: String?,
    @SerializedName("type")
    val type: String?
)