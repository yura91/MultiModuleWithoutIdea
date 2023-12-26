package net.pst.cash.data.repos

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.requests.AppleSignInRequest
import net.pst.cash.data.requests.GoogleSignInRequest
import net.pst.cash.data.responses.ErrorResponse
import net.pst.cash.data.responses.UserInfoResponse
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val sharedPref: SharedPreferences
) : SignInRepository {

    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    override suspend fun signInGoogle(registerHash: String, googleToken: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val signInResponse =
                    api.signInGoogle(GoogleSignInRequest(googleToken, registerHash))
                if (signInResponse.isSuccessful) {
                    val responseData = signInResponse.body()
                    val token: String? = responseData?.data?.token
                    with(sharedPref.edit()) {
                        putString(Companion.token, token)
                        apply()
                    }
                    token?.let {
                        val userInfoResponse = api.getUserInfo("Bearer $it")
                        val userInfoResponseBody: UserInfoResponse? = userInfoResponse.body()
                        val userId = userInfoResponseBody?.data?.userId
                        with(sharedPref.edit()) {
                            putString(Companion.userId, userId)
                            apply()
                        }
                    }
                    true
                } else {
                    val errorBody = signInResponse.errorBody()?.string()
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

    override suspend fun getAppleLink(): String? {
        return withContext(Dispatchers.IO) {
            try {
                val linkResponse = api.getAppleLink()
                if (linkResponse.isSuccessful) {
                    val appleUrl = linkResponse.body()?.data?.appleUrl
                    appleUrl
                } else {
                    val errorBody = linkResponse.errorBody()?.string()
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

    override suspend fun signInApple(code: String?, registerHash: String?): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val signInAppleResponse = api.signInApple(AppleSignInRequest(code, registerHash))
                if (signInAppleResponse.isSuccessful) {
                    val token = signInAppleResponse.body()?.data?.token
                    with(sharedPref.edit()) {
                        putString(token, token)
                        apply()
                    }
                    token?.let {
                        val userInfoResponse = api.getUserInfo("Bearer $it")
                        val userInfoResponseBody: UserInfoResponse? = userInfoResponse.body()
                        val userId = userInfoResponseBody?.data?.userId
                        with(sharedPref.edit()) {
                            putString(userId, userId)
                            apply()
                        }
                    }
                    true
                } else {
                    val errorBody = signInAppleResponse.errorBody()?.string()
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

    companion object {
        const val token = "token"
        const val userId = "userId"
    }
}