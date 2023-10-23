package net.pst.cash.data.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.ShowPanResponse
import javax.inject.Inject

class CardInfoRepoImpl @Inject constructor(
    private val api: ApiService
) : CardInfoRepo {
    override suspend fun getCardInfo(token: String, id: String): ShowPanResponse? {
         return withContext(Dispatchers.IO) {
             try {
                 val cardInfoResponse = api.getCardInfo(token, id)
                 if (cardInfoResponse.isSuccessful) {
                     val cardInfo = cardInfoResponse.body()
                     cardInfo
                 } else {
                     null
                 }
             } catch (e: Exception) {
                 null
            }
        }
    }
}
