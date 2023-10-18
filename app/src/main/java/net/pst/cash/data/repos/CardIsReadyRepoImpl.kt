package net.pst.cash.data.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.responses.ApiService
import net.pst.cash.data.responses.CardResponseData
import javax.inject.Inject

class CardIsReadyRepoImpl @Inject constructor(
    private val api: ApiService
) : CardIsReadyRepo {
    override suspend fun checkActiveCard(token: String): List<CardResponseData>? {
        return withContext(Dispatchers.IO) {
            val cardDataResponse = api.checkActiveCard(token)
            if (cardDataResponse.isSuccessful) {
                cardDataResponse.body()?.data
            } else {
                null
            }
        }

    }

}