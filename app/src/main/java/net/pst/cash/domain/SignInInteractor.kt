package net.pst.cash.domain

interface SignInInteractor {
    suspend fun signInGoogle(googleToken: String)

    suspend fun signInApple(code: String?)

    suspend fun getAppleLink(): String?
}