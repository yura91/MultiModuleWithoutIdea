package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class Addresses(
    @SerializedName("address") var address: String? = null,
    @SerializedName("payment_system_id") var paymentSystemId: Int? = null,
    @SerializedName("payment_url") var paymentUrl: String? = null
)