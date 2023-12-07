package net.pst.cash.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import net.pst.cash.domain.ActiveCardInteractor
import net.pst.cash.domain.ConfigInteractor
import net.pst.cash.domain.model.Tariff
import javax.inject.Inject

@HiltViewModel
class SelectBalanceViewModel @Inject constructor(
    private val application: Application,
    private val activeCardInteractor: ActiveCardInteractor,
    private val configInteractor: ConfigInteractor
) : AndroidViewModel(application) {
    var cardId: Int = 0
    var balance: String = ""
    var currencyType: String = ""
    var enouphMoney: Boolean = false
    var remainedFunds: String = ""
    var cardBalanceAmount: String = ""

    val configData: LiveData<List<Tariff>?> = configInteractor.configData.map {
        it?.tariffs
    }
}