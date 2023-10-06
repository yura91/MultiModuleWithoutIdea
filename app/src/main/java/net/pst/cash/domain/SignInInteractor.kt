package net.pst.cash.domain

import androidx.lifecycle.LiveData

interface SignInInteractor {
    val errorMessage: LiveData<String>

    suspend fun signInGoogle(googleToken: String): Boolean

    suspend fun signInApple(code: String?): Boolean

    suspend fun getAppleLink(): String?
}