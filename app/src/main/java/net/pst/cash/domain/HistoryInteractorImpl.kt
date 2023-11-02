package net.pst.cash.domain

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.pst.cash.data.paging.TransactionModel
import net.pst.cash.data.repos.HistoryListRepo
import net.pst.cash.domain.model.HistoryItem
import net.pst.cash.domain.model.RowHistoryItems
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class HistoryInteractorImpl @Inject constructor(private val historyListRepo: HistoryListRepo) :
    HistoryInteractor {

    override suspend fun getTransactionList(token: String): Flow<PagingData<RowHistoryItems>> {
        val rowHistoryItems = historyListRepo.getTransactionList(token)
        return rowHistoryItems.map { pagingData ->
            pagingData.map {
                val historyItems = mutableListOf<HistoryItem>()
                it.elements.forEach { historyItem ->
                    historyItems.add(
                        HistoryItem(
                            historyItem.sum,
                            historyItem.description,
                            historyItem.timePart
                        )
                    )
                }
                RowHistoryItems(it.date, historyItems)
            }
        }
    }


    private fun setDateAndTime(transactionModel: TransactionModel, processedAt: String) {
        // Форматируем строку даты и времени
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val date: Date = inputFormat.parse(processedAt)
        val formattedDateTime: String = outputFormat.format(date)

        // Выделяем дату и время
        val splitDateTime = formattedDateTime.split(" ")
        val datePart = splitDateTime[0]
        val timePart = splitDateTime[1]
        transactionModel.datePart = datePart
        transactionModel.timePart = timePart
    }
}