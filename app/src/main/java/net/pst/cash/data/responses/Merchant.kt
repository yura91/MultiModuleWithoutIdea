package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class Merchant(
    @SerializedName("name") var name: String? = null,
    @SerializedName("shortname") var shortname: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("file") var file: String? = null
)