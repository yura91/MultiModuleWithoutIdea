package net.pst.cash.domain

import net.pst.cash.data.repos.AccountsRepo
import net.pst.cash.domain.model.Account
import javax.inject.Inject

class AccountsInteractorImpl @Inject constructor(private val accountsRepo: AccountsRepo) :
    AccountsInteractor {
    override suspend fun getAccounts(token: String): Account? {
        val account = accountsRepo.getAccounts(token)?.accounts?.first {
            it.currencyId == 15
        }
        return if (account?.addresses?.get(0)?.address != null && account.balance != null) {
            Account(account.addresses[0].address!!, account.balance!! + " " + "USD")
        } else {
            null
        }
    }
}