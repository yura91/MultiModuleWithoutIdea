package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.Account
import net.pst.cash.data.responses.CardResponseData
import javax.inject.Inject

class ActiveCardsRepoImpl @Inject constructor(
    private val api: ApiService
) : ActiveCardsRepo {
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    override suspend fun checkActiveCard(token: String): List<CardResponseData> {
        val account = Account()
        account.balance = "150"
        account.currencyId = 2
        val cardDataResponse = CardResponseData()
        cardDataResponse.id = 0
        cardDataResponse.account = account
        return listOf(cardDataResponse)
    }
}