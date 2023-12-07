package net.pst.cash.data.requests

import com.google.gson.annotations.SerializedName

data class AppleSignInRequest(
    @SerializedName("code")
    val code: String?,
    @SerializedName("master_hash")
    val masterHash: String?
    /* @SerializedName("referral_hash")
     val referralHash: String = "c5f022971bfc5bd54622b52e0bb1d1fd",
     @SerializedName("is_app")
     val isApp: Boolean = true,
     @SerializedName("type")
     val type: Int = 1,
     @SerializedName("master_hash")
     val masterHash: String = "fqwoeipurqwjnflskdjgqwer"*/
)