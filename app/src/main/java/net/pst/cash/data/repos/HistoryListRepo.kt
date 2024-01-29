package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.pst.cash.data.paging.RowHistoryItems
import net.pst.cash.data.paging.TransactionModel

interface HistoryListRepo {
    val errorMessage: LiveData<String>
    suspend fun getTransactionList(token: String, cardId: String): Flow<PagingData<RowHistoryItems>>

    suspend fun getShortTransactionList(
        token: String,
        cardId: String
    ): Flow<Map<String, List<TransactionModel>>>
}