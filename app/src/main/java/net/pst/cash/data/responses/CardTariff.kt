package net.pst.cash.data.responses


import com.google.gson.annotations.SerializedName

data class CardTariff(
    @SerializedName("card_price")
    val cardPrice: String? = null,
    @SerializedName("duration")
    val duration: Int? = null,
    @SerializedName("fee_topup")
    val feeTopup: String? = null,
    @SerializedName("fee_transaction_amount")
    val feeTransactionAmount: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("is_allowed_for_user")
    val isAllowedForUser: Boolean? = null,
    @SerializedName("is_visible")
    val isVisible: Boolean? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("replace_price")
    val replacePrice: String? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("start_balance")
    val startBalance: String? = null,
    @SerializedName("type")
    val type: Int? = null
)