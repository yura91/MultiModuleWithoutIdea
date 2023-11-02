package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

import net.pst.cash.domain.HistoryInteractor
import net.pst.cash.domain.model.RowHistoryItems
import javax.inject.Inject

@HiltViewModel
class HistoryPaymentsViewModel @Inject constructor(
    private val application: Application,
    private val historyInteractor: HistoryInteractor,
) : AndroidViewModel(application) {

    private var date = ""

    suspend fun getTransactionHistory(): Flow<PagingData<RowHistoryItems>> {
        val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "")
        return historyInteractor.getTransactionList("Bearer $token")

    }
}