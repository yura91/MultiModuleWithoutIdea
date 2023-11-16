package net.pst.cash.data.repos

import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.AccountsResponse
import javax.inject.Inject

class AccountsRepoImpl @Inject constructor(
    private val api: ApiService
) : AccountsRepo {
    override suspend fun getAccounts(token: String): AccountsResponse? =
        api.getAccounts(token).body()
}