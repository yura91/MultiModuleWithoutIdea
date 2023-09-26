package net.pst.cash.data

import com.google.gson.annotations.SerializedName

data class GoogleSignInResponse(
    @SerializedName("token")
    val token: String
)