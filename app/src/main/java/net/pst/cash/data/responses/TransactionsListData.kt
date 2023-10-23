package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class TransactionsListData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("amount_total") var amountTotal: String? = null,
    @SerializedName("subtransactions") var subtransactions: ArrayList<String> = arrayListOf(),
    @SerializedName("user_account_id") var userAccountId: Int? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("account_iban") var accountIban: String? = null,
    @SerializedName("target_card_id") var targetCardId: Int? = null,
    @SerializedName("card_id") var cardId: Int? = null,
    @SerializedName("card_mask") var cardMask: String? = null,
    @SerializedName("card_description") var cardDescription: String? = null,
    @SerializedName("card_tariff") var cardTariff: ArrayList<CardTariff> = arrayListOf(),
    @SerializedName("currency_id") var currencyId: Int? = null,
    @SerializedName("amount_full") var amountFull: String? = null,
    @SerializedName("tax") var tax: String? = null,
    @SerializedName("fee") var fee: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("penalty_amount") var penaltyAmount: String? = null,
    @SerializedName("amount_default") var amountDefault: String? = null,
    @SerializedName("old_balance") var oldBalance: String? = null,
    @SerializedName("new_balance") var newBalance: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("comment") var comment: String? = null,
    @SerializedName("merchant") var merchant: Merchant? = Merchant(),
    @SerializedName("type_enum") var typeEnum: String? = null,
    @SerializedName("status_text") var statusText: String? = null,
    @SerializedName("custom_status") var customStatus: String? = null,
    @SerializedName("money_request_id") var moneyRequestId: Int? = null,
    @SerializedName("amount_original") var amountOriginal: String? = null,
    @SerializedName("currency_code_original") var currencyCodeOriginal: String? = null,
    @SerializedName("cashback_amount") var cashbackAmount: String? = null,
    @SerializedName("cashback_status") var cashbackStatus: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("processed_at") var processedAt: String? = null,
    @SerializedName("deleted_at") var deletedAt: String? = null
)