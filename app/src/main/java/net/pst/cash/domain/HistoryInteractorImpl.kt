package net.pst.cash.domain

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.pst.cash.data.repos.HistoryListRepo
import net.pst.cash.domain.model.HistoryItem
import net.pst.cash.domain.model.RowHistoryItems
import javax.inject.Inject

class HistoryInteractorImpl @Inject constructor(private val historyListRepo: HistoryListRepo) :
    HistoryInteractor {

    override suspend fun getTransactionList(token: String): Flow<PagingData<RowHistoryItems>> {
        val rowHistoryItems = historyListRepo.getTransactionList(token)
        return rowHistoryItems.map { pagingData ->
            pagingData.map {
                val historyItems = mutableListOf<HistoryItem>()
                it.elements.forEach { historyItem ->
                    historyItems.add(
                        HistoryItem(
                            historyItem.sum,
                            historyItem.description,
                            historyItem.timePart,
                            historyItem.status
                        )
                    )
                }
                RowHistoryItems(it.date, historyItems)
            }
        }
    }
}