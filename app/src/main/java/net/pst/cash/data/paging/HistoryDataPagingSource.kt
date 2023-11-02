package net.pst.cash.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.TransactionsListData
import java.text.SimpleDateFormat
import java.util.Date

class HistoryDataPagingSource(private val apiService: ApiService, private val token: String) :
    PagingSource<Int, TransactionModel>() {
    private var nextLink: String? = null
    override fun getRefreshKey(state: PagingState<Int, TransactionModel>): Int? {
        return null
    }


    override suspend fun load(params: LoadParams<Int>):
            PagingSource.LoadResult<Int, TransactionModel> {

        return try {
            val currentPage = params.key ?: 1
            var transactionModels = listOf<TransactionModel>()
            if (currentPage == 1) {
                val responseData = mutableListOf<TransactionsListData>()
                Log.d("CURRENTPAGE", "currentPage $currentPage")
                val response = apiService.getTransactionsList(token)
                nextLink = response.body()?.links?.next
                Log.d("NEXTLINK", "currentPage $nextLink")
                val data = response.body()?.data
                if (data != null) {
                    responseData.addAll(data)
                }
                transactionModels = responseData.map {
                    var dateTime = Pair("", "")
                    if (it.processedAt != null) {
                        dateTime = getDateAndTime(it.processedAt)
                    }
                    var sum = ""
                    if (it.processedAt != null) {
                        sum = it.processedAt
                    }
                    var description = ""
                    if (it.description != null) {
                        description = it.description
                    }
                    TransactionModel(sum, description, dateTime.first, dateTime.second)
                }
            } else {
                Log.d("NEXTLINK", "currentPage $nextLink")
                nextLink?.let {
                    val responseData = mutableListOf<TransactionsListData>()
                    Log.d("CURRENTPAGE", "currentPage $currentPage")
                    val replacedLink = it.replace("http", "https")
                    val listTransDataResp =
                        apiService.getMoreTransactions(token, replacedLink).body()
                    nextLink = listTransDataResp?.links?.next

                    val data = listTransDataResp?.data
                    if (data != null) {
                        responseData.addAll(data)
                    }
                    transactionModels = responseData.map { transactionListData ->
                        var dateTime = Pair("", "")
                        if (transactionListData.processedAt != null) {
                            dateTime = getDateAndTime(transactionListData.processedAt)
                        }
                        var sum = ""
                        if (transactionListData.processedAt != null) {
                            sum = transactionListData.processedAt
                        }
                        var description = ""
                        if (transactionListData.description != null) {
                            description = transactionListData.description
                        }
                        TransactionModel(sum, description, dateTime.first, dateTime.second)
                    }
                }
            }

            PagingSource.LoadResult.Page(
                data = transactionModels,
                prevKey = null,
                nextKey = if (nextLink != null) currentPage.plus(1) else null
            )
        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
    }

    private fun getDateAndTime(processedAt: String): Pair<String, String> {
        // Форматируем строку даты и времени
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val date: Date = inputFormat.parse(processedAt)
        val formattedDateTime: String = outputFormat.format(date)

        // Выделяем дату и время
        val splitDateTime = formattedDateTime.split(" ")
        val datePart = splitDateTime[0]
        val timePart = splitDateTime[1]
        return Pair(datePart, timePart)
    }
}