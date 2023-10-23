package net.pst.cash.domain

import net.pst.cash.data.repos.TransactionsListRepo
import javax.inject.Inject

class TransactionListInteractorImpl @Inject constructor(private val transactionsListRepo: TransactionsListRepo) :
    TransactionListInteractor {
    override suspend fun getTransactionList(token: String) {
        val transactionListData = transactionsListRepo.getTransactionList(token)
    }
}