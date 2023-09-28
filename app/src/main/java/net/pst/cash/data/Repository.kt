package net.pst.cash.data

interface Repository {
    suspend fun signInGoogle(googleToken: String): Boolean

    suspend fun getAppleLink(): String?

    suspend fun signInApple(code: String?)
}