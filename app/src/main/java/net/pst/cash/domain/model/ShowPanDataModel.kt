package net.pst.cash.domain.model

import com.google.gson.annotations.SerializedName


data class ShowPanDataModel(
    @SerializedName("number") var number: String? = null,
    @SerializedName("cvx2") var cvx2: String? = null,
    @SerializedName("exp_month") var expMonth: String? = null,
    @SerializedName("exp_year") var expYear: String? = null,
    @SerializedName("password") var password: String? = null

)