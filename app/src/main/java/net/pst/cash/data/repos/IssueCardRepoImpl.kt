package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.requests.IssueCardRequest
import net.pst.cash.data.responses.ErrorResponse
import net.pst.cash.data.responses.IssueCardResponse
import javax.inject.Inject

class IssueCardRepoImpl @Inject constructor(
    private val api: ApiService
) : IssueCardRepo {

    override val errorResponse: LiveData<ErrorResponse>
        get() = _errorResponse
    private val _errorResponse: MutableLiveData<ErrorResponse> = MutableLiveData()
    override val unknownError: LiveData<String>
        get() = _unknownError
    private val _unknownError: MutableLiveData<String> = MutableLiveData()

    override suspend fun issueCard(
        token: String,
        accountId: Int,
        cardBalance: String
    ): IssueCardResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val balance = cardBalance.split(" ")[0]
                val issueCardRequest =
                    IssueCardRequest(accountId = accountId, startBalance = balance)
                val apiResult = api.issueCard(token, issueCardRequest)
                if (apiResult.isSuccessful) {
                    apiResult.body()
                } else {
                    val errorBody = apiResult.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                    _errorResponse.postValue(errorResponse)
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _unknownError.postValue(e.message)
                null
            }
        }
    }
}