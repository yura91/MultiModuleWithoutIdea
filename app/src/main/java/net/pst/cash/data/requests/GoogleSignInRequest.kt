package net.pst.cash.data.requests

import com.google.gson.annotations.SerializedName

data class GoogleSignInRequest(
    @SerializedName("id_token")
    val idToken: String,
    @SerializedName("master_hash")
    val masterHash: String
)
