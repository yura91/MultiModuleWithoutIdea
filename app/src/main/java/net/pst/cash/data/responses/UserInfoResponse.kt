package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(@SerializedName("data") val data: UserInfoResponseData)
