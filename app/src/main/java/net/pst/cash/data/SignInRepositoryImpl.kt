package net.pst.cash.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val context: Context
) : SignInRepository {
    override suspend fun signInGoogle(googleToken: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val signInResponse = api.signInGoogle(GoogleSignInRequest(googleToken))
                if (signInResponse.isSuccessful) {
                    val responseData = signInResponse.body()
                    val token: String? = responseData?.data?.token
                    val sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("token", token)
                        apply()
                    }
                    true
                } else {
                    Log.d("TAG", "Failure")
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
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
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    override suspend fun signInApple(code: String?): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val signInAppleResponse = api.signInApple(AppleSignInRequest(code))
                if (signInAppleResponse.isSuccessful) {
                    val token = signInAppleResponse.body()?.data?.token
                    val sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("token", token)
                        apply()
                    }
                    true
                } else {
                    Log.d("TAG", "Failure")
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}