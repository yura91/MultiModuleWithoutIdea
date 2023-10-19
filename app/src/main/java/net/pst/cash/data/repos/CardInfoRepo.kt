package net.pst.cash.data.repos

interface CardInfoRepo {
    suspend fun getCardInfo(token: String, id: String)
}