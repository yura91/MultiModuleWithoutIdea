package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class AppleLinkData(
    @SerializedName("apple_url")
    val appleUrl: String? = null
)

data class AppleLinkResponse(val data: AppleLinkData? = null)