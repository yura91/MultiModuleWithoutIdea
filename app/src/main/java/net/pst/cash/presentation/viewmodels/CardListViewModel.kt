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
import net.pst.cash.domain.CardInfoInteractor
import net.pst.cash.domain.HistoryInteractor
import net.pst.cash.presentation.model.CardInfoData
import net.pst.cash.presentation.model.CardModel
import net.pst.cash.presentation.model.Currency
import net.pst.cash.presentation.model.HistoryItem
import net.pst.cash.presentation.model.RowHistoryItems
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    private val application: Application,
    private val accountsInteractor: AccountsInteractor,
    private val activeCardInteractor: ActiveCardInteractor,
    private val historyInteractor: HistoryInteractor,
    private val cardInfoInteractor: CardInfoInteractor
) : AndroidViewModel(application) {

    private val _cardList = MutableLiveData<List<CardModel>>()
    val cardList: LiveData<List<CardModel>>
        get() = _cardList

    private val _cardHistoriesList = MutableLiveData<List<CardModel>>()
    val cardHistoriesList: LiveData<List<CardModel>>
        get() = _cardHistoriesList

    val errorLoadCardList = activeCardInteractor.errorMessage

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _account = accountsInteractor.account.map {
        it?.balance?.split(" ")?.get(0)
    }

    val account: LiveData<String?>
        get() = _account

    fun getAllCards() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val cardList = activeCardInteractor.getAllCards("Bearer $token")
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

    fun getAllCardHistories() {
        val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "")
        viewModelScope.launch {
            val cardList = cardList.value
            if (!cardList.isNullOrEmpty()) {
                cardList.subList(0, cardList.size - 1).forEach { cardModel ->
                    val cardId = cardModel.id.toString()
                    if (token != null) {
                        historyInteractor.getShortTransactionList("Bearer $token", cardId)
                            .collect {
                                val payments = it.map { rowHistoryItem ->
                                    val historyItems = mutableListOf<HistoryItem>()
                                    rowHistoryItem.elements.forEach { historyItem ->
                                        historyItems.add(
                                            HistoryItem(
                                                historyItem.sum,
                                                historyItem.description,
                                                historyItem.timePart,
                                                historyItem.status
                                            )
                                        )
                                    }
                                    RowHistoryItems(rowHistoryItem.date, historyItems)
                                }
                                cardModel.rowHistoryItems.addAll(payments)
                                cardModel.rowHistoryItems.add(RowHistoryItems())
                            }
                    }
                }

                _cardHistoriesList.value = cardList
            }
        }
    }

    fun getCardInfo(cardId: String) {
        val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "")
        viewModelScope.launch {
            token?.let { token ->
                val cardInfoModel = cardInfoInteractor.getCardInfo("Bearer $token", cardId)
                val expMounth = cardInfoModel.expMonth
                val expYear = cardInfoModel.expYear
                val expDate = "$expMounth/$expYear"
                val cardInfoData = CardInfoData(cardInfoModel.number, cardInfoModel.cvx2, expDate)
                val cardList = cardList.value
                if (!cardList.isNullOrEmpty()) {
                    val cardUpdatedModel = cardList.first {
                        it.id.toString() == cardId
                    }

                    cardUpdatedModel.fullCardNumber = cardInfoData.number
                    cardUpdatedModel.cvv = cardInfoData.cvv
                    cardUpdatedModel.expireDate = cardInfoData.expDate

                    _cardList.value = cardList
                }
            }
        }
    }

    fun getActiveBalance() {
        viewModelScope.launch {
            accountsInteractor.getAccounts()
        }
    }
}