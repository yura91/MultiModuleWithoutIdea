package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.AccountsInteractor
import net.pst.cash.domain.ActiveCardInteractor
import net.pst.cash.presentation.model.CardModel
import net.pst.cash.presentation.model.Currency
import javax.inject.Inject

@HiltViewModel
class CardInfoViewModel @Inject constructor(
    private val application: Application,
    private val accountsInteractor: AccountsInteractor,
    private val activeCardInteractor: ActiveCardInteractor
) : AndroidViewModel(application) {
    var balance: String = ""
    private var accountId: Int? = null

    private val _cardModel = MutableLiveData<CardModel>()
    val cardModel: LiveData<CardModel>
        get() = _cardModel

    private val _account = accountsInteractor.account.map {
        it?.balance?.split(" ")?.get(0)
    }

    val account: LiveData<String?>
        get() = _account


    fun getActiveBalance() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            accountsInteractor.getAccounts()
            val activeCard = activeCardInteractor.getActiveCardModel("Bearer $token")
            activeCard?.let {
                val currencyType: String = when (it.currencyId) {
                    Currency.DOLAR.currencyCode -> "$"
                    Currency.EURO.currencyCode -> "€"
                    else -> {
                        ""
                    }
                }

                val cardModel = CardModel(it.id, currencyType, it.balance)
                _cardModel.value = cardModel
            }
        }
    }
}