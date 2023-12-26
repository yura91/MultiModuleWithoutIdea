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
import net.pst.cash.presentation.SingleLiveEvent
import net.pst.cash.presentation.model.BalanceItemModel
import net.pst.cash.presentation.model.ErrorType
import javax.inject.Inject


@HiltViewModel
class SelectBalanceViewModel @Inject constructor(
    private val application: Application,
    private val accountsInteractor: AccountsInteractor,
    private val issueCardInteractor: IssueCardInteractor,
    private val configInteractor: ConfigInteractor
) : AndroidViewModel(application) {
    var balanceCard: String = ""
    var firstCardCost: String = ""
    private var accountId: Int? = null
    var accountBalance: String? = null
    val notEnoughMoney: LiveData<Unit>
        get() = _notEnoughMoney

    private val _notEnoughMoney = SingleLiveEvent<Unit>()

    val snackBarErrorMessage = issueCardInteractor.errorModel.map {
        if (it.type == ErrorType.NOT_ENOUGH_MONEY.errorType) {
            _notEnoughMoney.value = Unit
        }
        it.message
    }

    private val _issueCardEvent = SingleLiveEvent<Unit>()
    val issueCardEvent: LiveData<Unit>
        get() = _issueCardEvent

    private val _account = accountsInteractor.account.map {
        accountId = it?.accountId
        accountBalance = it?.balance
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
        balanceCard = balanceItemModels[0].balanceAmount
        firstCardCost = balanceItemModels[0].usdt.split(" ")[0]
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
                    val issueCardResponse =
                        issueCardInteractor.issueCard("Bearer $token", accountId, balanceCard)
                    issueCardResponse?.let {
                        _issueCardEvent.value = Unit
                    }
                }
            }
        }
    }
}