package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class CardTariff(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("card_price") var cardPrice: String? = null,
    @SerializedName("replace_price") var replacePrice: String? = null,
    @SerializedName("start_balance") var startBalance: String? = null,
    @SerializedName("duration") var duration: Int? = null,
    @SerializedName("type") var type: Int? = null,
    @SerializedName("fee_topup") var feeTopup: String? = null,
    @SerializedName("fee_transaction_amount") var feeTransactionAmount: String? = null,
    @SerializedName("is_visible") var isVisible: Boolean? = null,
    @SerializedName("is_allowed_for_user") var isAllowedForUser: Boolean? = null
)