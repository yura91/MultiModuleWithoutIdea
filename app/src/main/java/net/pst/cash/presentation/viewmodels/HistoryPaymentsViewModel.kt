package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
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

    private var date = ""

    fun getTransactionHistory() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val transactionMap = historyInteractor.getTransactionList("Bearer $token")
            Log.d("COLLECTION_LIST", transactionMap.toString())
            val tableHistoryItems = mutableListOf<RowHistoryItems>()
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
            date = datePart

            val rowHistoryItems = RowHistoryItems(datePart, historyItems)
            tableHistoryItems.add(rowHistoryItems)
            Log.d("COLLECTION_LIST", tableHistoryItems.toString())
        }
        _transList.value = tableHistoryItems
        }
    }

    fun getMoreTransactions() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val transactionMap = historyInteractor.loadMoreTransactions("Bearer $token")
            val tableHistoryItems = mutableListOf<RowHistoryItems>()
            if (transactionMap != null) {
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
                    try {
                        val rowHistoryItems =
                            tableHistoryItems.first {
                                it.date == datePart
                            }
                        rowHistoryItems.elements.addAll(historyItems)
                    } catch (e: NoSuchElementException) {
                        if (datePart == date) {
                            val rowHistoryItems = RowHistoryItems("", historyItems)
                            tableHistoryItems.add(rowHistoryItems)
                        } else {
                            val rowHistoryItems = RowHistoryItems(datePart, historyItems)
                            tableHistoryItems.add(rowHistoryItems)
                            date = datePart
                        }

                    }
                }
                _transMoreList.value = tableHistoryItems
            }
        }
    }
}