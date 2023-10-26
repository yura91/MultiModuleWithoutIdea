package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.HistoryInteractor
import net.pst.cash.presentation.model.HistoryItem
import javax.inject.Inject

@HiltViewModel
class HistoryPaymentsViewModel @Inject constructor(
    private val application: Application,
    private val historyInteractor: HistoryInteractor,
) : AndroidViewModel(application) {
    private val historyItemsMap: MutableMap<String, List<HistoryItem>> = mutableMapOf()
    private val _transList = MutableLiveData<Map<String, List<HistoryItem>>>()
    val transList: LiveData<Map<String, List<HistoryItem>>>
        get() = _transList


    fun getTransactionHistory() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val transactionMap = historyInteractor.getTransactionList("Bearer $token")
            for ((datePart, transactions) in transactionMap) {
                val historyItems: MutableList<HistoryItem> = mutableListOf()
                for (transaction in transactions) {
                    val historyItem = HistoryItem(
                        sum = transaction.sum,
                        description = transaction.description,
                        timePart = transaction.timePart
                    )
                    historyItems.add(historyItem)
                }

                historyItemsMap[datePart] = historyItems
                _transList.value = historyItemsMap
            }
        }
    }

    fun getMoreTransactions() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val transactionMap = historyInteractor.loadMoreTransactions("Bearer $token")
            for ((datePart, transactions) in transactionMap) {
                val historyItems: MutableList<HistoryItem> = mutableListOf()
                for (transaction in transactions) {
                    val historyItem = HistoryItem(
                        sum = transaction.sum,
                        description = transaction.description,
                        timePart = transaction.timePart
                    )
                    historyItems.add(historyItem)
                }

                historyItemsMap[datePart] = historyItems
                _transList.value = historyItemsMap
            }
        }
    }
}