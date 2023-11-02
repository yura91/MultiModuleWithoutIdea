package net.pst.cash.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.pst.cash.domain.model.RowHistoryItems


interface HistoryInteractor {
    suspend fun getTransactionList(token: String): Flow<PagingData<RowHistoryItems>>
}