package net.pst.cash.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("/oauth/google/callback-jwt")
    suspend fun signInGoogle(@Body requestBody: GoogleSignInRequest): Response<GoogleSignInResponse>

    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("/oauth/apple/link")
    suspend fun getAppleLink(): Response<AppleLinkResponse>

    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("/oauth/apple/callback")
    suspend fun signInApple(@Body requestBody: AppleSignInRequest): Response<AppleSignInResponse>

    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("/dictionary/countries")
    suspend fun getCountriesList(): Response<CountriesListResponse>

    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("/verification/actual")
    suspend fun isVerificationNeeded(@Header("Authorization") token: String): Response<VerificationNeedResponse>

    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("/verification")
    suspend fun verifyUser(
        @Header("Authorization") token: String,
        @Body requestBody: VerificationRequest
    ): Response<VerificationResponse>
}


