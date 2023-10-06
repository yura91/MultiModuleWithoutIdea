package net.pst.cash.data

import androidx.lifecycle.LiveData

interface SignInRepository {
    val errorMessage: LiveData<String>

    suspend fun signInGoogle(googleToken: String): Boolean

    suspend fun getAppleLink(): String?

    suspend fun signInApple(code: String?): Boolean
}