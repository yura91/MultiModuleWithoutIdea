package net.pst.cash.domain

interface TransactionListInteractor {
    suspend fun getTransactionList(token: String)
}