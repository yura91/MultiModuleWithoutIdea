package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class Avatar(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("size") var size: Int? = null
)