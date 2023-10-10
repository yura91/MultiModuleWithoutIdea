package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import javax.inject.Inject

class UserInfoRepositoryimpl @Inject constructor(
    private val api: ApiService
) : UserInfoRepository {
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    override suspend fun getUserInfo(authToken: String): Boolean {
        return withContext(Dispatchers.IO) {
            val userInfoResponse = api.getUserInfo(authToken)
            userInfoResponse.isSuccessful
        }
    }
}