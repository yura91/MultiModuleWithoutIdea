package net.pst.cash.domain

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.pst.cash.data.repos.AccountsRepo
import net.pst.cash.domain.model.Account
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject


class AccountsInteractorImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val accountsRepo: AccountsRepo
) :
    AccountsInteractor {
    private val _account = MutableLiveData<Account?>()
    override val account = _account
    private var timer = Timer()

    override suspend fun getAccounts(token: String) {
        timer.schedule(object : TimerTask() {
            override fun run() {
                GlobalScope.launch {
                    val tokenValue = sharedPreferences.getString("token", "")
                    val account = accountsRepo.getAccounts("Bearer $tokenValue")?.accounts?.first {
                        it.currencyId == 15
                    }
                    val accAddress = account?.addresses?.get(0)?.address
                    val accBalance = account?.balance
                    val accountId = account?.id

                    if (accAddress != null && accBalance != null && accountId != null) {
                        val roundedBalance = roundOffDecimal(accBalance.toDouble())
                        val accountValue =
                            Account(accountId, accAddress, roundedBalance.toString())
                        _account.postValue(accountValue)
                    } else {
                        _account.postValue(null)
                    }
                }
            }
        }, 0, 60000)
    }

    fun roundOffDecimal(number: Double): Double {
        return Math.round(number * 100.0) / 100.0
    }
}