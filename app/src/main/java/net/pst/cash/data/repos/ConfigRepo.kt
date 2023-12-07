package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import net.pst.cash.data.responses.ConfigResponse

interface ConfigRepo {
    val errorMessage: LiveData<String>
    suspend fun getConfig(): ConfigResponse?
}