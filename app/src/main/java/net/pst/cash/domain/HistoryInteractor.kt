package net.pst.cash.domain

import net.pst.cash.domain.model.TransactionModel

interface HistoryInteractor {
    suspend fun getTransactionList(token: String): Map<String, List<TransactionModel>>
}