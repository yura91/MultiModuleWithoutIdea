package net.pst.cash.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("/oauth/google/callback-jwt")
    suspend fun signInGoogle(@Body requestBody: GoogleSignInRequest): Response<GoogleSignInResponse>

    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("/oauth/apple/link")
    suspend fun getAppleLink(): Response<AppleLinkResponse>
}


