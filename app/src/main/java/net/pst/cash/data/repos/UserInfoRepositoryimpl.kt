package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.ErrorResponse
import javax.inject.Inject

class UserInfoRepositoryimpl @Inject constructor(
    private val api: ApiService
) : UserInfoRepository {
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    override suspend fun getUserInfo(authToken: String): Boolean {
        return withContext(Dispatchers.IO) {
            val userInfoResponse = api.getUserInfo()
            if (userInfoResponse.isSuccessful) {
                true
            } else {
                val errorBody = userInfoResponse.errorBody()?.string()
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                _errorMessage.postValue(errorResponse.message)
                false
            }
        }
    }
}