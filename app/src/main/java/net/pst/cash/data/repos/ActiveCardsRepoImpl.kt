package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.requests.DeleteCardRequest
import net.pst.cash.data.responses.CardResponseData
import net.pst.cash.data.responses.ErrorResponse
import javax.inject.Inject

class ActiveCardsRepoImpl @Inject constructor(
    private val api: ApiService
) : ActiveCardsRepo {
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    override suspend fun getAllCards(token: String): List<CardResponseData>? {
        return withContext(Dispatchers.IO) {
            try {
                val cardDataResponse = api.getAllCards(token)
                if (cardDataResponse.isSuccessful) {
                    cardDataResponse.body()?.data
                } else {
                    val errorBody = cardDataResponse.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                    _errorMessage.postValue(errorResponse.message)
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue(e.message)
                null
            }
        }
    }

    override suspend fun updateCard(token: String, cardId: String): CardResponseData? {
        return withContext(Dispatchers.IO) {
            try {
                val cardDataResponse = api.updateCard(token, cardId)
                if (cardDataResponse.isSuccessful) {
                    cardDataResponse.body()?.data
                } else {
                    val errorBody = cardDataResponse.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                    _errorMessage.postValue(errorResponse.message)
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue(e.message)
                null
            }
        }
    }

    override suspend fun deleteCard(token: String, cardId: String, accountId: Int?): Boolean? {
        return withContext(Dispatchers.IO) {
            try {
                val cardDataResponse = api.deleteCard(token, cardId, DeleteCardRequest(accountId))
                if (cardDataResponse.isSuccessful) {
                    cardDataResponse.body()?.success
                } else {
                    val errorBody = cardDataResponse.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                    _errorMessage.postValue(errorResponse.message)
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue(e.message)
                false
            }
        }
    }
}