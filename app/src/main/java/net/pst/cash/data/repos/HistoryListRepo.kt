package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.pst.cash.data.paging.TransactionModel
import net.pst.cash.data.responses.TransactionsListData

interface HistoryListRepo {
    val errorMessage: LiveData<String>
    suspend fun getTransactionList(token: String): Flow<PagingData<TransactionModel>>

    suspend fun loadMoreTransactions(token: String): List<TransactionsListData>?
}