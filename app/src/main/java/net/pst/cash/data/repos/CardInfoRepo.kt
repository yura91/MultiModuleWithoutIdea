package net.pst.cash.data.repos

import net.pst.cash.data.responses.ShowPanResponse

interface CardInfoRepo {
    suspend fun getCardInfo(token: String, id: String): ShowPanResponse?
}