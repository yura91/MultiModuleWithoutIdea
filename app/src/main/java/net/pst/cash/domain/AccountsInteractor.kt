package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.domain.model.Account

interface AccountsInteractor {

    val account: LiveData<Account?>
    suspend fun getAccounts()
}