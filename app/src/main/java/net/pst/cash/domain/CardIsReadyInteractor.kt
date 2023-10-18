package net.pst.cash.domain

import net.pst.cash.data.responses.CardResponseData

interface CardIsReadyInteractor {
    suspend fun checkActiveCard(token: String): List<CardResponseData>?
}