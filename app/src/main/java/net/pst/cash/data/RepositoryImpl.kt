package net.pst.cash.data

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: ApiService) : Repository {
    override fun googleSignIn(googleToken: String) {
        val signInResponse = api.signInGoogle(GoogleSignInRequest(googleToken))
        signInResponse.enqueue(object : Callback<GoogleSignInResponse> {
            override fun onResponse(
                call: Call<GoogleSignInResponse>,
                response: Response<GoogleSignInResponse>
            ) {
                val token = response.body()?.token
            }

            override fun onFailure(call: Call<GoogleSignInResponse>, t: Throwable) {
                Log.d("TAG", "Failure")
            }

        })
    }
}