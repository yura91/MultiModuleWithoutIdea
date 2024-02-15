package net.pst.cash.domain

import androidx.lifecycle.LiveData

interface SignInInteractor {
    val errorMessage: LiveData<String>

    suspend fun signInGoogle(registerHash: String, googleToken: String): Boolean

    suspend fun signInEmail(email: String, password: String): Boolean

    suspend fun signInApple(code: String?, registerHash: String?): Boolean

    suspend fun getAppleLink(): String?
}