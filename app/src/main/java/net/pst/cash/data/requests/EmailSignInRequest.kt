package net.pst.cash.data.requests

import com.google.gson.annotations.SerializedName

data class EmailSignInRequest(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)
