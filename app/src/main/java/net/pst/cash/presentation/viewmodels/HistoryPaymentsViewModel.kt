package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.TransactionListInteractor
import net.pst.cash.presentation.model.HistoryPaymentModel
import javax.inject.Inject

@HiltViewModel
class HistoryPaymentsViewModel @Inject constructor(
    private val application: Application,
    private val transactionListInteractor: TransactionListInteractor,
) : AndroidViewModel(application) {
    private val _transList = MutableLiveData<List<HistoryPaymentModel>>()
    val transList: LiveData<List<HistoryPaymentModel>>
        get() = _transList


    fun getTransactionHistory() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val transactionMap = transactionListInteractor.getTransactionList("Bearer $token")

            transactionMap.let { transactions ->
                transactions.forEach {
                }
            }
        }
    }
}