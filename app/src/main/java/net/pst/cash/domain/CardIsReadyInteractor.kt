package net.pst.cash.domain

interface CardIsReadyInteractor {
    suspend fun getActiveCardId(token: String): Int?
}