package net.pst.cash.domain

import net.pst.cash.data.repos.HistoryListRepo
import net.pst.cash.domain.model.TransactionModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class HistoryInteractorImpl @Inject constructor(private val historyListRepo: HistoryListRepo) :
    HistoryInteractor {
    override suspend fun getTransactionList(token: String): Map<String, List<TransactionModel>> {
        val transactionListData = historyListRepo.getTransactionList(token)
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
        /* val transModel1 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel2 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel3 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel4 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel5 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel6 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel7 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel8 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel9 = TransactionModel("100", "ffdfsdf", "21.10.2023", "13:05:33")
         val transModel10 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel11 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel12 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel13 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel14 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel15 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         val transModel16 = TransactionModel("100", "ffdfsdf", "20.10.2023", "13:05:33")
         transactionModels.add(transModel1)
         transactionModels.add(transModel2)
         transactionModels.add(transModel3)
         transactionModels.add(transModel4)
         transactionModels.add(transModel5)
         transactionModels.add(transModel6)
         transactionModels.add(transModel7)
         transactionModels.add(transModel8)
         transactionModels.add(transModel9)
         transactionModels.add(transModel10)
         transactionModels.add(transModel11)
         transactionModels.add(transModel12)
         transactionModels.add(transModel13)
         transactionModels.add(transModel14)
         transactionModels.add(transModel15)
         transactionModels.add(transModel16)*/
        return transactionModels.groupBy {
            it.datePart
        }
    }

    override suspend fun loadMoreTransactions(token: String): Map<String, List<TransactionModel>> {
        val transactionListData = historyListRepo.loadMoreTransactions(token)
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