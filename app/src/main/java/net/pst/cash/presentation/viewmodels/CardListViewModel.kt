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
class CardListViewModel @Inject constructor(
    private val application: Application,
    private val accountsInteractor: AccountsInteractor,
    private val activeCardInteractor: ActiveCardInteractor
) : AndroidViewModel(application) {
    var balance: String = ""

    private val _cardList = MutableLiveData<List<CardModel>>()
    val cardList: LiveData<List<CardModel>>
        get() = _cardList

    private val _account = accountsInteractor.account.map {
        it?.balance?.split(" ")?.get(0)
    }

    val account: LiveData<String?>
        get() = _account

    fun getAllCards() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val cardList = activeCardInteractor.getActiveCardModel("Bearer $token")
            val cards: MutableList<CardModel> = mutableListOf()
            cardList?.let {
                it.forEach { cardModel ->
                    val currencyType: String = when (cardModel.currencyId) {
                        Currency.DOLAR.currencyCode -> "$"
                        Currency.EURO.currencyCode -> "€"
                        else -> {
                            ""
                        }
                    }
                    val card = CardModel(
                        cardModel.id,
                        currencyType,
                        cardModel.balance,
                        cardModel.holderName,
                        cardModel.lastCardDigits,
                        cardModel.expireDate
                    )
                    cards.add(card)
                }
            }
            cards.add(CardModel())
            _cardList.value = cards
        }
    }

    fun getActiveBalance() {
        viewModelScope.launch {
            accountsInteractor.getAccounts()
        }
    }
}