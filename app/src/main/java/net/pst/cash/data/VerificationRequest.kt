package net.pst.cash.data

import com.google.gson.annotations.SerializedName

data class VerificationRequest(
    @SerializedName("step")
    val step: String? = "unlimited",
    @SerializedName("first_name")
    val firstName: String = "John",
    @SerializedName("last_name")
    val lastName: String = "Wick",
    @SerializedName("birthday")
    val birthday: String = "1985-01-01",
    @SerializedName("country_id")
    val countryId: String = "1",
    @SerializedName("actual_country_id")
    val actualCountryId: String = "1",
    @SerializedName("is_not_located_in_country")
    val isNotLocatedAnyCountry: Boolean = true
)
