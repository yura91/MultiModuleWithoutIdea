package net.pst.cash.data

interface SignInRepository {
    suspend fun signInGoogle(googleToken: String): Boolean

    suspend fun getAppleLink(): String?

    suspend fun signInApple(code: String?): Boolean
}