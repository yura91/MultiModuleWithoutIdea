package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class Account(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("type") var type: Int? = null,
    @SerializedName("currency_id") var currencyId: Int? = null,
    @SerializedName("iban") var iban: String? = null,
    @SerializedName("balance") var balance: String? = null,
    @SerializedName("balance_default") var balanceDefault: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("addresses") var addresses: ArrayList<Addresses> = arrayListOf(),
    @SerializedName("summary") var summary: CheckResponseSummary? = CheckResponseSummary()
)