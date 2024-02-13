package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class AllCardsResponse(
    @SerializedName("data") var data: ArrayList<CardResponseData> = arrayListOf()
)