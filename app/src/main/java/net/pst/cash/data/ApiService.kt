package net.pst.cash.data

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("/oauth/google/callback-jwt")
    fun signInGoogle(@Body requestBody: GoogleSignInRequest): MyCall<GoogleSignInResponse>
}


