package net.pst.cash.data

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val context: Context
) : Repository {
    override fun googleSignIn(googleToken: String) {
        val signInResponse = api.signInGoogle(GoogleSignInRequest(googleToken))
        signInResponse.enqueue(object : Callback<GoogleSignInResponse> {
            override fun onResponse(
                call: Call<GoogleSignInResponse>,
                response: Response<GoogleSignInResponse>
            ) {
                val token = response.body()?.data?.token
                val sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("token", token)
                    apply()
                }
            }

            override fun onFailure(call: Call<GoogleSignInResponse>, t: Throwable) {
                Log.d("TAG", "Failure")
            }

        })
    }
}