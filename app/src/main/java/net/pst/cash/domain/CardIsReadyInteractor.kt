package net.pst.cash.domain

import net.pst.cash.domain.model.CardModel

interface CardIsReadyInteractor {
    suspend fun getActiveCardModel(token: String): CardModel?
}