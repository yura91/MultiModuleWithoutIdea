package net.pst.cash.domain

interface CardInfoInteractor {
    suspend fun getCardInfo(token: String, id: String)
}