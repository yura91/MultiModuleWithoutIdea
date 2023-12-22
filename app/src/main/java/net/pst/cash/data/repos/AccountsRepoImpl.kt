package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.AccountsResponse
import net.pst.cash.data.responses.ErrorResponse
import javax.inject.Inject

class AccountsRepoImpl @Inject constructor(
    private val api: ApiService
) : AccountsRepo {
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    override suspend fun getAccounts(token: String): AccountsResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val apiResult = api.getAccounts(token)
                if (apiResult.isSuccessful) {
                    return@withContext apiResult.body()
                } else {
                    val errorBody = apiResult.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                    _errorMessage.postValue(errorResponse.message)
                    return@withContext null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue(e.message)
                return@withContext null
            }
        }
    }
}