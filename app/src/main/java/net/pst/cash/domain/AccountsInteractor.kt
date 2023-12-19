package net.pst.cash.domain

import net.pst.cash.domain.model.Account

interface AccountsInteractor {
    suspend fun getAccounts(token: String): Account?
}