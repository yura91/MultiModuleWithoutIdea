package net.pst.cash.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import net.pst.cash.domain.ActiveCardInteractor
import javax.inject.Inject

@HiltViewModel
class SelectBalanceViewModel @Inject constructor(
    private val application: Application,
    private val activeCardInteractor: ActiveCardInteractor
) : AndroidViewModel(application) {
    var cardId: Int = 0
    var balance: String = ""
    var currencyType: String = ""
    var enouphMoney: Boolean = false
    var remainedFunds: String = ""
    var cardBalanceAmount: String = ""
}