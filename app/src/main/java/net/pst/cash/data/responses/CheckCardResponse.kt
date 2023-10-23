package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class CheckCardResponse(
    @SerializedName("data") var data: ArrayList<CardResponseData> = arrayListOf()
)