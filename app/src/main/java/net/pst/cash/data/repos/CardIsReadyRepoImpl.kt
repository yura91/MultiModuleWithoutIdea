package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.CardResponseData
import javax.inject.Inject

class CardIsReadyRepoImpl @Inject constructor(
    private val api: ApiService
) : CardIsReadyRepo {
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    override suspend fun checkActiveCard(token: String): List<CardResponseData>? {
        return withContext(Dispatchers.IO) {
            try {
                val cardDataResponse = api.checkActiveCard(token)
                if (cardDataResponse.isSuccessful) {
                    cardDataResponse.body()?.data
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue(e.message)
                null
            }

        }

    }
}