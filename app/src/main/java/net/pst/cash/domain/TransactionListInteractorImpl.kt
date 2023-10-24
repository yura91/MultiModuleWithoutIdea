package net.pst.cash.domain

import net.pst.cash.data.repos.TransactionsListRepo
import net.pst.cash.domain.model.TransactionModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class TransactionListInteractorImpl @Inject constructor(private val transactionsListRepo: TransactionsListRepo) :
    TransactionListInteractor {
    override suspend fun getTransactionList(token: String): Map<String, List<TransactionModel>> {
        val transactionListData = transactionsListRepo.getTransactionList(token)
        val transactionModels: MutableList<TransactionModel> = mutableListOf()
        transactionListData?.forEach {
            val transModel = TransactionModel()
            it.amountTotal?.let { amount ->
                transModel.sum = amount
            }

            it.description?.let { description ->
                transModel.description = description
            }

            it.processedAt?.let { processedAt ->
                setDateAndTime(transModel, processedAt)
            }
            transactionModels.add(transModel)
        }

        return transactionModels.groupBy {
            it.datePart
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