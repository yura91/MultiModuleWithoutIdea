package net.pst.cash.domain

import net.pst.cash.domain.model.ShowPanDataModel

interface CardInfoInteractor {
    suspend fun getCardInfo(token: String, id: String): ShowPanDataModel
}