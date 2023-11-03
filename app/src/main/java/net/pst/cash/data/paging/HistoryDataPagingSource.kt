package net.pst.cash.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.TransactionsListData
import java.text.SimpleDateFormat
import java.util.Date

class HistoryDataPagingSource(private val apiService: ApiService, private val token: String) :
    PagingSource<Int, RowHistoryItems>() {
    private var nextLink: String? = null
    private var date = ""

    override fun getRefreshKey(state: PagingState<Int, RowHistoryItems>): Int? {
        return null
    }


    override suspend fun load(params: LoadParams<Int>):
            PagingSource.LoadResult<Int, RowHistoryItems> {

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
                        if (transactionListData.amount != null) {
                            sum = transactionListData.amount
                        }
                        var description = ""
                        if (transactionListData.description != null) {
                            description = transactionListData.description
                        }
                        var status: Int = 0
                        if (transactionListData.status != null) {
                            status = transactionListData.status
                        }
                        TransactionModel(sum, description, dateTime.first, dateTime.second, status)
                    }
                }
            }

            val rowHistoryitems = mutableListOf<RowHistoryItems>()

            transactionModels.map {
                val datepart = it.datePart
                try {
                    val rowHistoryItem = rowHistoryitems.first { rowHistItems ->
                        rowHistItems.date == datepart
                    }
                    val historyItem = HistoryItem(it.sum, it.description, it.timePart, it.status)
                    rowHistoryItem.elements.add(historyItem)
                } catch (e: NoSuchElementException) {
                    val historyItems = mutableListOf<HistoryItem>()
                    historyItems.add(HistoryItem(it.sum, it.description, it.timePart, it.status))
                    rowHistoryitems.add(RowHistoryItems(datepart, historyItems))
                }
            }

            rowHistoryitems.forEach {
                if (it.date == date) {
                    it.date = ""
                } else {
                    date = it.date
                }
            }

            PagingSource.LoadResult.Page(
                data = rowHistoryitems,
                prevKey = null,
                nextKey = if (nextLink != null) currentPage.plus(1) else null
            )
        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
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