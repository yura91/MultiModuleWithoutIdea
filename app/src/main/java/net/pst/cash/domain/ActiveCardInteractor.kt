package net.pst.cash.domain

import net.pst.cash.domain.model.CardModel

interface ActiveCardInteractor {
    suspend fun getActiveCardModel(token: String): CardModel?
}