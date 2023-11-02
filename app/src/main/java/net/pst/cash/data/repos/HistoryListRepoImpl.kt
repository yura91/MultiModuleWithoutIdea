package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.pst.cash.data.ApiService
import net.pst.cash.data.paging.HistoryDataPagingSource
import net.pst.cash.data.paging.RowHistoryItems
import javax.inject.Inject

class HistoryListRepoImpl @Inject constructor(
    private val api: ApiService
) : HistoryListRepo {
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    override suspend fun getTransactionList(token: String): Flow<PagingData<RowHistoryItems>> {
        val listData = Pager(PagingConfig(pageSize = 25)) {
            HistoryDataPagingSource(api, token)

        }.flow
        return listData
    }
}