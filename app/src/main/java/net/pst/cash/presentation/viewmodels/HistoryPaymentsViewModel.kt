package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import net.pst.cash.data.paging.TransactionModel
import net.pst.cash.domain.HistoryInteractor
import net.pst.cash.presentation.model.HistoryItem
import net.pst.cash.presentation.model.RowHistoryItems
import net.pst.cash.presentation.model.toList
import javax.inject.Inject

@HiltViewModel
class HistoryPaymentsViewModel @Inject constructor(
    private val application: Application,
    private val historyInteractor: HistoryInteractor,
) : AndroidViewModel(application) {

    private var date = ""

    suspend fun getTransactionHistory(): Flow<PagingData<TransactionModel>> {
        val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "")
        return historyInteractor.getTransactionList("Bearer $token")

    }

    fun transformData(input: Flow<PagingData<TransactionModel>>): Flow<PagingData<RowHistoryItems>> {
        return input.flatMapLatest { pagingData ->
            Log.d("PAGINGDATA", pagingData.toString())
            val list = mutableListOf<RowHistoryItems>()
            val transactionList = pagingData.toList()
            val grouped = transactionList.groupBy { it.datePart }
            for ((date, items) in grouped) {
                val historyItems = items.map {
                    HistoryItem(it.sum, it.description, it.timePart)
                }
                list.add(RowHistoryItems(date, historyItems.toMutableList()))
            }
            flowOf(PagingData.from(list))
        }
    }
}