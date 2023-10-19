package net.pst.cash.data.responses

import com.google.gson.annotations.SerializedName


data class ShowPanResponse(
    @SerializedName("data") var data: ShowPanData?
)