package net.pst.cash.domain

import androidx.lifecycle.MutableLiveData
import net.pst.cash.data.repos.AccountsRepo
import net.pst.cash.domain.model.Account
import javax.inject.Inject

class AccountsInteractorImpl @Inject constructor(private val accountsRepo: AccountsRepo) :
    AccountsInteractor {
    private val _account = MutableLiveData<Account?>()
    override val account = _account
    override suspend fun getAccounts(token: String) {
        val account = accountsRepo.getAccounts(token)?.accounts?.first {
            it.currencyId == 15
        }
        if (account?.addresses?.get(0)?.address != null && account.balance != null) {
            val account = Account(account.addresses[0].address!!, account.balance!! + " " + "USD")
            _account.value = account
        } else {
            _account.value = null
        }
    }
}