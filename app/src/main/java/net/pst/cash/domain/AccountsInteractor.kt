package net.pst.cash.domain

interface AccountsInteractor {
    suspend fun getAccounts(token: String): List<String>
}