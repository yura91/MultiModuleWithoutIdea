package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class Tags(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("is_system") var isSystem: Boolean? = null
)