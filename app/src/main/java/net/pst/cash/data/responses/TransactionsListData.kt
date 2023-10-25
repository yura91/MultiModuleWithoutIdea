package net.pst.cash.data.responses


import com.google.gson.annotations.SerializedName

data class TransactionsListData(
    @SerializedName("account_iban")
    val accountIban: String? = "",
    @SerializedName("amount")
    val amount: String? = "",
    @SerializedName("amount_default")
    val amountDefault: String? = "",
    @SerializedName("amount_original")
    val amountOriginal: String? = "",
    @SerializedName("amount_total")
    val amountTotal: String? = "",
    @SerializedName("card_description")
    val cardDescription: String? = "",
    @SerializedName("card_id")
    val cardId: Int? = 0,
    @SerializedName("card_mask")
    val cardMask: String? = "",
    @SerializedName("card_tariff")
    val cardTariff: CardTariff? = CardTariff(),
    @SerializedName("cashback_amount")
    val cashbackAmount: Any? = Any(),
    @SerializedName("cashback_status")
    val cashbackStatus: Any? = Any(),
    @SerializedName("comment")
    val comment: Any? = Any(),
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("currency_code_original")
    val currencyCodeOriginal: String? = "",
    @SerializedName("currency_id")
    val currencyId: Int? = 0,
    @SerializedName("custom_status")
    val customStatus: String? = "",
    @SerializedName("deleted_at")
    val deletedAt: Any? = Any(),
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("merchant")
    val merchant: Any? = Any(),
    @SerializedName("money_request_id")
    val moneyRequestId: Any? = Any(),
    @SerializedName("new_balance")
    val newBalance: String? = "",
    @SerializedName("old_balance")
    val oldBalance: String? = "",
    @SerializedName("penalty_amount")
    val penaltyAmount: String? = "",
    @SerializedName("processed_at")
    val processedAt: String? = "",
    @SerializedName("status")
    val status: Int? = 0,
    @SerializedName("status_text")
    val statusText: String? = "",
    @SerializedName("subtransactions")
    val subtransactions: List<Any>? = listOf(),
    @SerializedName("target_card_id")
    val targetCardId: Any? = Any(),
    @SerializedName("tax")
    val tax: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("type_enum")
    val typeEnum: String? = "",
    @SerializedName("user_account_id")
    val userAccountId: Int? = 0
)