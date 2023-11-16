package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName

data class AccountsResponse(
    @SerializedName("data") var accounts: ArrayList<Account> = arrayListOf()
)
