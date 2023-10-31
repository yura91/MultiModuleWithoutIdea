package net.pst.cash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.TransactionsListData
import net.pst.cash.domain.model.TransactionModel
import java.text.SimpleDateFormat
import java.util.Date

class HistoryDataPagingSource(private val apiService: ApiService, private val token: String) :
    PagingSource<Int, TransactionModel>() {

    override fun getRefreshKey(state: PagingState<Int, TransactionModel>): Int? {
        return null
    }


    override suspend fun load(params: LoadParams<Int>):
            PagingSource.LoadResult<Int, TransactionModel> {

        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getTransactionsList(token)
            val responseData = mutableListOf<TransactionsListData>()
            val data = response.body()?.data
            if (data != null) {
                responseData.addAll(data)
            }

            val transactionModels = responseData.map {
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
            PagingSource.LoadResult.Page(
                data = transactionModels,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
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