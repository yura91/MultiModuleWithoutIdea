package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import net.pst.cash.data.ApiService
import net.pst.cash.data.paging.HistoryDataPagingSource
import net.pst.cash.data.paging.RowHistoryItems
import net.pst.cash.data.paging.TransactionModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class HistoryListRepoImpl @Inject constructor(
    private val api: ApiService
) : HistoryListRepo {
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    override suspend fun getTransactionList(
        token: String,
        cardId: String
    ): Flow<PagingData<RowHistoryItems>> {
        val listData = Pager(PagingConfig(pageSize = 1)) {
            HistoryDataPagingSource(api, token, cardId)

        }.flow
        return listData
    }

    override suspend fun getShortTransactionList(
        token: String,
        cardId: String
    ): Flow<Map<String, List<TransactionModel>>> {
        val response = api.getTransactionsList(token, cardId)
        val data = response.body()?.data
        val transactionModels = mutableListOf<TransactionModel>()
        val transactions = data?.take(4)?.map {
            var dateTime = Pair("", "")
            if (it.processedAt != null) {
                dateTime = getDateAndTime(it.processedAt)
            }
            var sum = ""
            if (it.amount != null) {
                sum = it.amount
            }
            var description = ""
            if (it.description != null) {
                description = it.description
            }
            var status: Int = 0
            if (it.status != null) {
                status = it.status
            }
            TransactionModel(sum, description, dateTime.first, dateTime.second, status)
        }
        if (!transactions.isNullOrEmpty()) {
            transactionModels.addAll(transactions)
        }
        val arrangedByDateModels = transactionModels.groupBy { it.datePart }
        return flowOf(arrangedByDateModels)
    }

    private fun getDateAndTime(processedAt: String): Pair<String, String> {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val date: Date = inputFormat.parse(processedAt)
        val formattedDateTime: String = outputFormat.format(date)

        val splitDateTime = formattedDateTime.split(" ")
        val datePart = splitDateTime[0]
        val timePart = splitDateTime[1]
        return Pair(datePart, timePart)
    }
}