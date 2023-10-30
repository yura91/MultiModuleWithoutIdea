package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.TransactionsListData
import javax.inject.Inject

class HistoryListRepoImpl @Inject constructor(
    private val api: ApiService
) : HistoryListRepo {
    private var nextLink: String? = null
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    override suspend fun getTransactionList(token: String): List<TransactionsListData>? {
        return withContext(Dispatchers.IO) {
            try {
                val transactionsListResponse = api.getTransactionsList(token)
                if (transactionsListResponse.isSuccessful) {
                    nextLink = transactionsListResponse.body()?.links?.next
                    transactionsListResponse.body()?.data
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue(e.message)
                null
            }
        }
    }

    override suspend fun loadMoreTransactions(token: String): List<TransactionsListData>? {
        return withContext(Dispatchers.IO) {
            try {
                nextLink?.let {
                    val replacedLink = it.replace("http", "https")
                    val listTransDataResp = api.getMoreTransactions(token, replacedLink).body()
                    nextLink = listTransDataResp?.links?.next
                    return@withContext listTransDataResp?.data
                }
                return@withContext null
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue(e.message)
                null
            }
        }
    }
}