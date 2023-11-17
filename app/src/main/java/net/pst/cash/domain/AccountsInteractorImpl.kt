package net.pst.cash.domain

import net.pst.cash.data.repos.AccountsRepo
import javax.inject.Inject

class AccountsInteractorImpl @Inject constructor(private val accountsRepo: AccountsRepo) :
    AccountsInteractor {
    override suspend fun getAccounts(token: String): List<String> {
        val interestingAccountAddresses: MutableList<String> = mutableListOf()
        val account = accountsRepo.getAccounts(token)?.accounts?.first {
            it.currencyId == 15
        }
        val fullAccountAddresses = account?.addresses
        fullAccountAddresses?.forEach {
            val address = it.address
            if (address != null) {
                interestingAccountAddresses.add(address)
            }
        }
        return interestingAccountAddresses
    }
}