package net.pst.cash.data

import com.google.gson.annotations.SerializedName

data class GoogleSignInResponse(val data: Data)
data class Data(
    @SerializedName("token")
    val token: String,
    @SerializedName("type")
    val type: String
)