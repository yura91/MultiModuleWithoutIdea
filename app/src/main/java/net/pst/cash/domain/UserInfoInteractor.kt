package net.pst.cash.domain

import androidx.lifecycle.LiveData

interface UserInfoInteractor {
    val errorMessage: LiveData<String>

    suspend fun getUserinfo(authToken: String): Boolean
}