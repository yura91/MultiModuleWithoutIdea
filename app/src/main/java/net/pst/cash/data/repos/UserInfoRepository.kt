package net.pst.cash.data.repos

import androidx.lifecycle.LiveData

interface UserInfoRepository {
    val errorMessage: LiveData<String>

    suspend fun getUserInfo(authToken: String): Boolean
}