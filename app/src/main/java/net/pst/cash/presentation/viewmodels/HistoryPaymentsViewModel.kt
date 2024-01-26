package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import net.pst.cash.domain.HistoryInteractor
import net.pst.cash.presentation.model.HistoryItem

import net.pst.cash.presentation.model.RowHistoryItems

import javax.inject.Inject

@HiltViewModel
class HistoryPaymentsViewModel @Inject constructor(
    private val application: Application,
    private val historyInteractor: HistoryInteractor,
) : AndroidViewModel(application) {

    suspend fun getTransactionHistory(cardId: String): Flow<PagingData<RowHistoryItems>> {
        val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "")
        val transactionList = historyInteractor.getTransactionList("Bearer $token", cardId)
        return transactionList.map { pagingData ->
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