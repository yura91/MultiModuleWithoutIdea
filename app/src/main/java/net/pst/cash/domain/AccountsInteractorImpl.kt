package net.pst.cash.domain

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.pst.cash.data.repos.AccountsRepo
import net.pst.cash.domain.model.Account
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject


class AccountsInteractorImpl @Inject constructor(private val accountsRepo: AccountsRepo) :
    AccountsInteractor {
    private val _account = MutableLiveData<Account?>()
    override val account = _account
    private var timer = Timer()

    override suspend fun getAccounts(token: String) {
        timer.schedule(object : TimerTask() {
            override fun run() {
                GlobalScope.launch {
                    val account = accountsRepo.getAccounts(token)?.accounts?.first {
                        it.currencyId == 15
                    }
                    val accAddress = account?.addresses?.get(0)?.address
                    val accBalance = account?.balance

                    if (accAddress != null && accBalance != null) {
                        val roundedBalance = roundOffDecimal(accBalance.toDouble())
                        val accountValue =
                            Account(accAddress, roundedBalance.toString())
                        _account.postValue(accountValue)
                    } else {
                        _account.postValue(null)
                    }
                }
            }
        }, 0, 60000)
    }

    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }
}