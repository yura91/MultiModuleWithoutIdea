package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.pst.cash.data.ApiService
import net.pst.cash.data.paging.HistoryDataPagingSource
import net.pst.cash.data.paging.TransactionModel
import javax.inject.Inject

class HistoryListRepoImpl @Inject constructor(
    private val api: ApiService
) : HistoryListRepo {
    private var nextLink: String? = null
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    override suspend fun getTransactionList(token: String): Flow<PagingData<TransactionModel>> {
        val listData = Pager(PagingConfig(pageSize = 25)) {
            HistoryDataPagingSource(api, token)

        }.flow
        return listData
    }
}