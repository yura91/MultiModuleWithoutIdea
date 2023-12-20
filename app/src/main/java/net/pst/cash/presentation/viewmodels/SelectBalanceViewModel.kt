package net.pst.cash.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import net.pst.cash.domain.AccountsInteractor
import net.pst.cash.domain.ConfigInteractor
import net.pst.cash.presentation.model.BalanceItemModel
import net.pst.cash.presentation.model.SelectedBalanceModel
import javax.inject.Inject


@HiltViewModel
class SelectBalanceViewModel @Inject constructor(
    private val application: Application,
    private val accountsInteractor: AccountsInteractor,
    private val configInteractor: ConfigInteractor
) : AndroidViewModel(application) {
    var cardId: Int = 0
    var balance: String = ""
    var currencyType: String = ""
    var enouphMoney: Boolean = false
    var remainedFunds: String = ""
    var cardBalanceAmount: String = ""

    val mediatorLiveData = MediatorLiveData<SelectedBalanceModel?>()

    private val _account = accountsInteractor.account.map {
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
        balanceItemModels
    }

    init {
        mediatorLiveData.addSource<String>(account) { account: String? ->
            val configItems: List<BalanceItemModel>? = configData.value
            if (!configItems.isNullOrEmpty()) {
                val selectedBalanceModel = SelectedBalanceModel(configItems, account)
                mediatorLiveData.setValue(selectedBalanceModel)
            } else {
                mediatorLiveData.setValue(null)
            }
        }

        mediatorLiveData.addSource<List<BalanceItemModel>?>(configData) { configData: List<BalanceItemModel>? ->
            val account: String? = account.value
            if (account != null) {
                val selectedBalanceModel = SelectedBalanceModel(configData, account)
                mediatorLiveData.setValue(selectedBalanceModel)
            } else {
                mediatorLiveData.setValue(null)
            }
        }

    }

}