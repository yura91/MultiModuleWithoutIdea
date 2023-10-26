package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import net.pst.cash.data.responses.TransactionsListData

interface HistoryListRepo {
    val errorMessage: LiveData<String>
    suspend fun getTransactionList(token: String): List<TransactionsListData>?

    suspend fun loadMoreTransactions(token: String): List<TransactionsListData>?
}