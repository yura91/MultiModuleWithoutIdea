package net.pst.cash.data.repos

import net.pst.cash.data.responses.TransactionsListData

interface TransactionsListRepo {
    suspend fun getTransactionList(token: String): List<TransactionsListData>?
}