package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName

data class EmailSignInResponse(
    @SerializedName("token")
    val token: String?
)
