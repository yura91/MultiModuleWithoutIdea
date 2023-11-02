package net.pst.cash.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.pst.cash.data.paging.RowHistoryItems

interface HistoryInteractor {
    suspend fun getTransactionList(token: String): Flow<PagingData<RowHistoryItems>>
}