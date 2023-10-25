package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class UserInfoSummary(
    @SerializedName("cards_count") var cardsCount: Int? = null,
    @SerializedName("cards_count_active") var cardsCountActive: Int? = null,
    @SerializedName("cards_balance") var cardsBalance: String? = null,
    @SerializedName("cards_balance_active") var cardsBalanceActive: String? = null,
    @SerializedName("cards_balance_inactive") var cardsBalanceInactive: String? = null,
    @SerializedName("accounts_balance") var accountsBalance: String? = null,
    @SerializedName("accounts_balance_active") var accountsBalanceActive: String? = null,
    @SerializedName("positive_transactions_sum") var positiveTransactionsSum: String? = null,
    @SerializedName("negative_transactions_sum") var negativeTransactionsSum: String? = null,
    @SerializedName("transactions_count") var transactionsCount: Int? = null
)