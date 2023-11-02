package net.pst.cash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import net.pst.cash.data.ApiService
import java.text.SimpleDateFormat
import java.util.Date

class HistoryDataPagingSource(private val apiService: ApiService, private val token: String) :
    PagingSource<Int, RowHistoryItems>() {
    private var date = ""
    private var startIndex = 0

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

        val transModel40 = TransactionModel("30", "ffdfsdf", "25.10.2023", "16:05:33")
        val transModel41 = TransactionModel("30", "ffdfsdf", "25.10.2023", "16:05:33")
        val transModel42 = TransactionModel("30", "ffdfsdf", "25.10.2023", "16:05:33")
        val transModel43 = TransactionModel("30", "ffdfsdf", "25.10.2023", "16:05:33")
        val transModel44 = TransactionModel("30", "ff", "25.10.2023", "16:05:33")
        val transModel45 = TransactionModel("30", "ffdfsdf", "25.10.2023", "16:05:33")
        val transModel46 = TransactionModel("30", "ffd", "25.10.2023", "16:05:33")
        val transModel47 = TransactionModel("30", "ffdfsdf", "25.10.2023", "16:05:33")

        transModels.add(transModel40)
        transModels.add(transModel41)
        transModels.add(transModel42)
        transModels.add(transModel43)
        transModels.add(transModel44)
        transModels.add(transModel45)
        transModels.add(transModel46)
        transModels.add(transModel47)

        val transModel48 = TransactionModel("10", "ffdfsdf", "25.10.2024", "16:05:33")
        val transModel49 = TransactionModel("10", "ffdfsdf", "25.10.2024", "16:05:33")
        val transModel50 = TransactionModel("10", "ff", "25.10.2024", "16:05:33")
        val transModel51 = TransactionModel("10", "ffd", "25.10.2024", "16:05:33")
        val transModel52 = TransactionModel("10", "ffd", "25.11.2024", "16:05:33")
        val transModel53 = TransactionModel("10", "ffd", "22.12.2024", "16:05:33")
        val transModel54 = TransactionModel("10", "ffds", "22.12.2024", "16:05:33")
        val transModel55 = TransactionModel("10", "ffde", "22.12.2024", "16:05:33")


        transModels.add(transModel48)
        transModels.add(transModel49)
        transModels.add(transModel50)
        transModels.add(transModel51)
        transModels.add(transModel52)
        transModels.add(transModel53)
        transModels.add(transModel54)
        transModels.add(transModel55)
    }

    override fun getRefreshKey(state: PagingState<Int, RowHistoryItems>): Int? {
        return null
    }


    override suspend fun load(params: LoadParams<Int>):
            PagingSource.LoadResult<Int, RowHistoryItems> {

        return try {
            val currentPage = params.key ?: 1

            val rowHistoryitems = mutableListOf<RowHistoryItems>()
            val endIndex = startIndex + 5
            if (endIndex < transModels.size) {
                val trModels = transModels.subList(startIndex, endIndex)
                trModels.map {
                    val datepart = it.datePart
                    try {
                        val rowHistoryItem = rowHistoryitems.first { rowHistItems ->
                            rowHistItems.date == datepart
                        }
                        val historyItem = HistoryItem(it.sum, it.description, it.timePart)
                        rowHistoryItem.elements.add(historyItem)
                    } catch (e: NoSuchElementException) {
                        val historyItems = mutableListOf<HistoryItem>()
                        historyItems.add(HistoryItem(it.sum, it.description, it.timePart))
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
                startIndex = endIndex
                PagingSource.LoadResult.Page(
                    data = rowHistoryitems,
                    prevKey = null,
                    nextKey = currentPage.plus(1)
                )
            } else {
                val trModels = transModels.subList(startIndex, transModels.size)
                trModels.map {
                    val datepart = it.datePart
                    try {
                        val rowHistoryItem = rowHistoryitems.first { rowHistItems ->
                            rowHistItems.date == datepart
                        }
                        val historyItem = HistoryItem(it.sum, it.description, it.timePart)
                        rowHistoryItem.elements.add(historyItem)
                    } catch (e: NoSuchElementException) {
                        val historyItems = mutableListOf<HistoryItem>()
                        historyItems.add(HistoryItem(it.sum, it.description, it.timePart))
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
                    nextKey = null
                )
            }
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