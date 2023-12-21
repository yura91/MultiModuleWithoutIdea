package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName

data class IssueCardResponse(
    @SerializedName("data") var data: IssueCardData? = IssueCardData()
)

data class IssueCardData(
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
    @SerializedName("has_waiting_transactions") var hasWaitingTransactions: Boolean? = null,
    @SerializedName("account") var account: Account? = Account(),
    @SerializedName("tariff_id") var tariffId: Int? = null,
    @SerializedName("need_amount") var needAmount: String? = null,
    @SerializedName("limits_equals") var limitsEquals: Boolean? = null,
    @SerializedName("tags") var tags: ArrayList<Tags> = arrayListOf(),
    @SerializedName("subscription_tariff") var subscriptionTariff: SubscriptionTariff? = SubscriptionTariff()
)

data class Tags(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("is_system") var isSystem: Boolean? = null
)

data class SubscriptionTariff(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("renewal") var renewal: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("currency_id") var currencyId: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("amount_first") var amountFirst: String? = null,
    @SerializedName("cards_limit") var cardsLimit: Int? = null,
    @SerializedName("cashback_percent") var cashbackPercent: String? = null,
    @SerializedName("cashback_limit") var cashbackLimit: Int? = null,
    @SerializedName("cashback_spend_limit") var cashbackSpendLimit: String? = null,
    @SerializedName("fee_topup") var feeTopup: String? = null,
    @SerializedName("transaction_fee") var transactionFee: String? = null,
    @SerializedName("renewal_name") var renewalName: String? = null,
    @SerializedName("type_name") var typeName: String? = null,
    @SerializedName("status_name") var statusName: String? = null
)

