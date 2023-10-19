package net.pst.cash.data.repos

import net.pst.cash.data.ApiService
import javax.inject.Inject

class CardInfoRepoImpl @Inject constructor(
    private val api: ApiService
) : CardInfoRepo {
    override suspend fun getCardInfo(token: String, id: String) {
        api.getCardInfo(token, id)
    }
}