package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class UserInfoResponse(
    @SerializedName("type") var type: Int? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("referral_hash") var referralHash: String? = null,
    @SerializedName("skype") var skype: String? = null,
    @SerializedName("telegram") var telegram: String? = null,
    @SerializedName("language_id") var languageId: Int? = null,
    @SerializedName("default_currency_id") var defaultCurrencyId: Int? = null,
    @SerializedName("country_id") var countryId: Int? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("avatar") var avatar: ArrayList<Avatar> = arrayListOf(),
    @SerializedName("roles") var roles: ArrayList<String> = arrayListOf(),
    @SerializedName("is_verified") var isVerified: Boolean? = null,
    @SerializedName("summary") var summary: UserInfoSummary? = UserInfoSummary(),
    @SerializedName("registration_method") var registrationMethod: String? = null,
    @SerializedName("token_name") var tokenName: String? = null,
    @SerializedName("is_old_interface_available") var isOldInterfaceAvailable: Boolean? = null,
    @SerializedName("has_special") var hasSpecial: Boolean? = null,
    @SerializedName("from_card_delay") var fromCardDelay: Int? = null,
    @SerializedName("show_adv") var showAdv: Boolean? = null,
    @SerializedName("show_warn") var showWarn: Boolean? = null,
    @SerializedName("is_old_user_for_subscription") var isOldUserForSubscription: Boolean? = null
)