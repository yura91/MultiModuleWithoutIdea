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
import net.pst.cash.presentation.model.RowHistoryItems
import javax.inject.Inject

@HiltViewModel
class HistoryPaymentsViewModel @Inject constructor(
    private val application: Application,
    private val historyInteractor: HistoryInteractor,
) : AndroidViewModel(application) {
    private val _transList = MutableLiveData<List<RowHistoryItems>>()
    val transList: LiveData<List<RowHistoryItems>>
        get() = _transList

    private val _transMoreList = MutableLiveData<List<RowHistoryItems>>()
    val transMoreList: LiveData<List<RowHistoryItems>>
        get() = _transMoreList

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
                val tableHistoryItems = mutableListOf<RowHistoryItems>()
                val rowHistoryItems = RowHistoryItems(datePart, historyItems)
                tableHistoryItems.add(rowHistoryItems)
                _transList.value = tableHistoryItems
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
                val tableHistoryItems = mutableListOf<RowHistoryItems>()
                for (transaction in transactions) {
                    val historyItem = HistoryItem(
                        sum = transaction.sum,
                        description = transaction.description,
                        timePart = transaction.timePart
                    )
                    historyItems.add(historyItem)
                }
                try {
                    val rowHistoryItems =
                        tableHistoryItems.first {
                            it.date == datePart
                        }
                    rowHistoryItems.elements.addAll(historyItems)
                } catch (e: NoSuchElementException) {

                    val rowHistoryItems = RowHistoryItems(datePart, historyItems)
                    tableHistoryItems.add(rowHistoryItems)
                }
                _transMoreList.value = tableHistoryItems
            }
        }
    }
}