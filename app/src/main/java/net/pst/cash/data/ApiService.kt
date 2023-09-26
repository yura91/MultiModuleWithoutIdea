package net.pst.cash.data

import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("/oauth/google/callback-jwt")
    fun signInGoogle(@Body requestBody: GoogleSignInRequest): MyCall<GoogleSignInResponse>
}


