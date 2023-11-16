package net.pst.cash.data.repos

import net.pst.cash.data.responses.AccountsResponse


interface AccountsRepo {
    suspend fun getAccounts(token: String): AccountsResponse?
}