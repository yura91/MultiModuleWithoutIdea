package net.pst.cash.domain

import android.util.Log
import kotlinx.coroutines.delay
import net.pst.cash.data.repos.HistoryListRepo
import net.pst.cash.domain.model.TransactionModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class HistoryInteractorImpl @Inject constructor(private val historyListRepo: HistoryListRepo) :
    HistoryInteractor {

    var index: Int = 0
    var endReached = false
    val transModels: MutableList<TransactionModel> = mutableListOf()

    init {
        val transModel1 = TransactionModel("100", "ffd", "20.10.2023", "13:05:33")
        val transModel2 = TransactionModel("100", "ff", "20.10.2023", "13:10:33")
        val transModel3 = TransactionModel("100", "ffab", "20.10.2023", "13:15:33")
        val transModel5 = TransactionModel("100", "ffabc", "20.10.2023", "13:25:33")
        val transModel6 = TransactionModel("100", "ffdfc", "20.10.2023", "13:35:33")
        val transModel7 = TransactionModel("100", "fdfse", "20.10.2023", "13:45:33")
        val transModel8 = TransactionModel("100", "feeeee", "20.10.2023", "13:55:33")
        transModels.add(transModel1)
        transModels.add(transModel2)
        transModels.add(transModel3)
        transModels.add(transModel5)
        transModels.add(transModel6)
        transModels.add(transModel7)
        transModels.add(transModel8)

        val transModel9 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        val transModel17 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        val transModel18 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        val transModel19 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        val transModel20 = TransactionModel("50", "ff", "22.10.2023", "14:05:33")
        val transModel21 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        val transModel22 = TransactionModel("50", "ffd", "22.10.2023", "14:05:33")
        val transModel23 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        transModels.add(transModel9)
        transModels.add(transModel17)
        transModels.add(transModel18)
        transModels.add(transModel19)
        transModels.add(transModel20)
        transModels.add(transModel21)
        transModels.add(transModel22)
        transModels.add(transModel23)

        val transModel24 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        val transModel25 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        val transModel26 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        val transModel27 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        val transModel28 = TransactionModel("50", "ff", "22.10.2023", "14:05:33")
        val transModel29 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")
        val transModel30 = TransactionModel("50", "ffd", "22.10.2023", "14:05:33")
        val transModel31 = TransactionModel("50", "ffdfsdf", "22.10.2023", "14:05:33")

        transModels.add(transModel24)
        transModels.add(transModel25)
        transModels.add(transModel26)
        transModels.add(transModel27)
        transModels.add(transModel28)
        transModels.add(transModel29)
        transModels.add(transModel30)
        transModels.add(transModel31)

        val transModel32 = TransactionModel("30", "ffdfsdf", "24.10.2023", "15:05:33")
        val transModel33 = TransactionModel("30", "ffdfsdf", "24.10.2023", "15:05:33")
        val transModel34 = TransactionModel("30", "ffdfsdf", "24.10.2023", "15:05:33")
        val transModel35 = TransactionModel("30", "ffdfsdf", "24.10.2023", "15:05:33")
        val transModel36 = TransactionModel("30", "ff", "24.10.2023", "15:05:33")
        val transModel37 = TransactionModel("30", "ffdfsdf", "24.10.2023", "15:05:33")
        val transModel38 = TransactionModel("30", "ffd", "24.10.2023", "15:05:33")
        val transModel39 = TransactionModel("30", "ffdfsdf", "25.10.2023", "15:05:33")

        transModels.add(transModel32)
        transModels.add(transModel33)
        transModels.add(transModel34)
        transModels.add(transModel35)
        transModels.add(transModel36)
        transModels.add(transModel37)
        transModels.add(transModel38)
        transModels.add(transModel39)
    }

    override fun getTransactionList(token: String): Map<String, List<TransactionModel>>? {
        if (transModels.size == 0) {
            return null
        }
        val transactionModels = transModels.subList(index, 5)
        index += 5
        Log.d("COLLECTION_LIST", transactionModels.toString())
        return transactionModels.groupBy {
            it.datePart
        }
    }

    override suspend fun loadMoreTransactions(token: String): Map<String, List<TransactionModel>>? {
        if (!endReached) {
            delay(2000)
            val toIndex = index + 5
            if (toIndex < transModels.size) {
                val transactionModels = transModels.subList(index, toIndex)
                index += 5
                return transactionModels.groupBy {
                    it.datePart
                }
            } else {
                val transactionModels = transModels.subList(index, transModels.size)
                endReached = true
                return transactionModels.groupBy {
                    it.datePart
                }
            }
        } else {
            return null
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