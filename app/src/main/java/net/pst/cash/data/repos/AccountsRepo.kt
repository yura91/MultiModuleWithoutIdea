package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import net.pst.cash.data.responses.AccountsResponse


interface AccountsRepo {
    val errorMessage: LiveData<String>
    suspend fun getAccounts(token: String): AccountsResponse?
}