package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import net.pst.cash.data.ApiService
import net.pst.cash.data.requests.IssueCardRequest
import net.pst.cash.data.responses.ErrorResponse
import net.pst.cash.data.responses.IssueCardResponse
import javax.inject.Inject

class IssueCardRepoImpl @Inject constructor(
    private val api: ApiService
) : IssueCardRepo {

    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    override suspend fun issueCard(
        token: String,
        accountId: Int,
        cardBalance: String
    ): IssueCardResponse? {
        try {
            val balance = cardBalance.split(" ")[0]
            val issueCardRequest =
                IssueCardRequest(accountId = accountId, startBalance = balance)
            val apiResult = api.issueCard(token, issueCardRequest)
            if (apiResult.isSuccessful) {
                return apiResult.body()
            } else {
                val errorBody = apiResult.errorBody()?.string()
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                _errorMessage.postValue(errorResponse.message)
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _errorMessage.postValue(e.message)
            return null
        }
    }
}