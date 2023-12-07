package net.pst.cash.data.repos

import androidx.lifecycle.LiveData

interface SignInRepository {
    val errorMessage: LiveData<String>

    suspend fun signInGoogle(registerHash: String, googleToken: String): Boolean

    suspend fun getAppleLink(): String?

    suspend fun signInApple(code: String?): Boolean
}