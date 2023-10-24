package net.pst.cash.data.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.TransactionsData
import javax.inject.Inject

class TransactionsListRepoImpl @Inject constructor(
    private val api: ApiService
) : TransactionsListRepo {
    override suspend fun getTransactionList(token: String): List<TransactionsData>? {
        return withContext(Dispatchers.IO) {
            val transactionsListResponse = api.getTransactionsList(token)
            if (transactionsListResponse.isSuccessful) {
                transactionsListResponse.body()?.data
            } else {
                null
            }
        }
    }
}