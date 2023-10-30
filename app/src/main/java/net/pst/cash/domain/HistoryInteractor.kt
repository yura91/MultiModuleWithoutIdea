package net.pst.cash.domain

import net.pst.cash.domain.model.TransactionModel

interface HistoryInteractor {
    fun getTransactionList(token: String): Map<String, List<TransactionModel>>?

    suspend fun loadMoreTransactions(token: String): Map<String, List<TransactionModel>>?
}