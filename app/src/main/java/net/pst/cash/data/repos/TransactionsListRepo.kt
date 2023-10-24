package net.pst.cash.data.repos

import net.pst.cash.data.responses.TransactionsData

interface TransactionsListRepo {
    suspend fun getTransactionList(token: String): List<TransactionsData>?
}