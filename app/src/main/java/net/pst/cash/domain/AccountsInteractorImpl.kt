package net.pst.cash.domain

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.pst.cash.data.repos.AccountsRepo
import net.pst.cash.domain.model.Account
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import kotlin.math.roundToInt


class AccountsInteractorImpl @Inject constructor(
    private val accountsRepo: AccountsRepo
) :
    AccountsInteractor {
    private val _account = MutableLiveData<Account?>()
    override val account = _account
    private var timer = Timer()

    override suspend fun getAccounts() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                GlobalScope.launch {
                    val account = accountsRepo.getAccounts()?.accounts?.first {
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
        }, delay, timeInMillis)
    }

    fun roundOffDecimal(number: Double): Double {
        return (number * divider).roundToInt() / divider
    }

    companion object {
        const val divider = 100.0
        const val timeInMillis: Long = 60000
        const val delay: Long = 0
    }
}