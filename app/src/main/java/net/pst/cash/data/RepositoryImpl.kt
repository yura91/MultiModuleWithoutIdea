package net.pst.cash.data

import android.content.Context
import android.util.Log
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val context: Context
) : Repository {
    override suspend fun googleSignIn(googleToken: String) {
        try {
            val signInResponse = api.signInGoogle(GoogleSignInRequest(googleToken))
            if (signInResponse.isSuccessful) {
                val responseData = signInResponse.body()
                val token = responseData?.data?.token
                val sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("authorization", token)
                    apply()
                }
            } else {
                Log.d("TAG", "Failure")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}