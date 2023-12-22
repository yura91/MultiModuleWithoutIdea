package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.AccountsInteractor
import net.pst.cash.domain.ConfigInteractor
import net.pst.cash.domain.IssueCardInteractor
import net.pst.cash.presentation.model.BalanceItemModel
import javax.inject.Inject


@HiltViewModel
class SelectBalanceViewModel @Inject constructor(
    private val application: Application,
    private val accountsInteractor: AccountsInteractor,
    private val issueCardInteractor: IssueCardInteractor,
    private val configInteractor: ConfigInteractor
) : AndroidViewModel(application) {
    var cardId: Int = 0
    var balance: String = ""
    var currencyType: String = ""
    var enouphMoney: Boolean = false
    var remainedFunds: String = ""
    var cardBalanceAmount: String = ""
    private var accountId: Int? = null

    private val _account = accountsInteractor.account.map {
        accountId = it?.accountId
        it?.balance?.split(" ")?.get(0)
    }
    val account: LiveData<String?>
        get() = _account

    val configData: LiveData<List<BalanceItemModel>?> = configInteractor.configData.map {
        val tariffs = it?.tariffs
        val balanceItemModels = mutableListOf<BalanceItemModel>()
        tariffs?.forEach { tariff ->
            val balanceModel = BalanceItemModel(
                tariff.balance.toString() + " $",
                tariff.price.toString() + " USDT"
            )
            balanceItemModels.add(balanceModel)
        }
        balance = balanceItemModels[0].balanceAmount
        balanceItemModels
    }

    fun getActiveBalance() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            accountsInteractor.getAccounts("Bearer $token")
        }
    }

    fun issueCard() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            token?.let { tokenValue ->
                accountId?.let { accountId ->
                    issueCardInteractor.issueCard("Bearer $token", accountId, balance)
                }
            }
        }
    }
}