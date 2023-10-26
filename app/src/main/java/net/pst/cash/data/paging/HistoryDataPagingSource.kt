package net.pst.cash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.TransactionsListData


class HistoryDataPagingSource(private val apiService: ApiService, private val token: String) :
    PagingSource<Int, TransactionsListData>() {

    override fun getRefreshKey(state: PagingState<Int, TransactionsListData>): Int? {
        return null
    }


    override suspend fun load(params: LoadParams<Int>):
            PagingSource.LoadResult<Int, TransactionsListData> {

        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getTransactionsList(token)
            val responseData = mutableListOf<TransactionsListData>()
            val data = response.body()?.data
            if (data != null) {
                responseData.addAll(data)
            }

            PagingSource.LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
    }
}