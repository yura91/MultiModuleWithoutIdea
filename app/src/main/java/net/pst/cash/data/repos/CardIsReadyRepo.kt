package net.pst.cash.data.repos

import net.pst.cash.data.responses.CardResponseData

interface CardIsReadyRepo {
    suspend fun checkActiveCard(token: String): List<CardResponseData>?
}