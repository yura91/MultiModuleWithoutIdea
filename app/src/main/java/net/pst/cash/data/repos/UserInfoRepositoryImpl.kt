package net.pst.cash.data.repos

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.UserInfoResponse
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val context: Context
) : UserInfoRepository {
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    override suspend fun getUserInfo(authToken: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val userInfoResponse = api.getUserInfo(authToken)
                if (userInfoResponse.isSuccessful) {
                    val userInfoResponseBody: UserInfoResponse? = userInfoResponse.body()
                    val userId = userInfoResponseBody?.data?.userId
                    val sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("userId", userId)
                        apply()
                    }
                }
                userInfoResponse.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue(e.message)
                false
            }
        }
    }
}