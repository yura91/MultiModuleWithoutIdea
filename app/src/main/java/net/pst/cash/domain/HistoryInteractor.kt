package net.pst.cash.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.pst.cash.domain.model.RowHistoryItems


interface HistoryInteractor {
    suspend fun getShortHistory(token: String, cardId: String): Flow<List<RowHistoryItems>>
    suspend fun getPaymentsHistory(token: String, cardId: String): Flow<PagingData<RowHistoryItems>>
}