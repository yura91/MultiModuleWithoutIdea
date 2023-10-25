package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class CardResponseData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("user_account_id") var userAccountId: Int? = null,
    @SerializedName("holder_name") var holderName: String? = null,
    @SerializedName("holder_address") var holderAddress: String? = null,
    @SerializedName("mask") var mask: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("favorite") var favorite: Int? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("ordered_at") var orderedAt: String? = null,
    @SerializedName("ordered_until") var orderedUntil: String? = null,
    @SerializedName("topup_waiting") var topupWaiting: Boolean? = null,
    @SerializedName("account") var account: Account? = Account(),
    @SerializedName("tariff_id") var tariffId: Int? = null,
    @SerializedName("need_amount") var needAmount: String? = null,
    @SerializedName("limits_equals") var limitsEquals: Boolean? = null,
    @SerializedName("tags") var tags: ArrayList<CardTags> = arrayListOf()
)