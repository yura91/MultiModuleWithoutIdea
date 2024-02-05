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

    private val _cardInfoList = MutableLiveData<List<CardModel>>()

    val cardInfoList: LiveData<List<CardModel>>
        get() = _cardInfoList

    private val _cardInfoModelPos = MutableLiveData<Int>()

    val cardInfoModelPos: LiveData<Int>
        get() = _cardInfoModelPos

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
                it.forEach { cardData ->
                    val currencyType: String = when (cardData.currencyId) {
                        Currency.DOLAR.currencyCode -> "$"
                        Currency.EURO.currencyCode -> "€"
                        else -> {
                            ""
                        }
                    }
                    val card = CardModel(
                        cardData.id,
                        currencyType,
                        cardData.balance,
                        lastCardDigits = cardData.lastFourDigits
                    )
                    cards.add(card)
                }
            }
            cards.add(CardModel())
            _cardList.value = cards
        }
    }

    fun getAllCardHistories() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
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

    fun getCardInfo(cardId: Int?) {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val cardList = cardList.value
            val cardModelIndex = cardList?.indexOfFirst {
                it.id == cardId
            }
            cardModelIndex?.let { index ->
                cardList[index].let {
                    token?.let { token ->
                        if (it.fullCardNumber == null && it.cvv == null && it.expireDate == null) {
                            val showPanDataModel =
                                cardInfoInteractor.getCardInfo("Bearer $token", cardId.toString())
                            it.fullCardNumber = showPanDataModel.number
                            val expMonth = showPanDataModel.expMonth
                            val expYear = showPanDataModel.expYear
                            it.expireDate = "$expMonth/$expYear"
                            it.cvv = showPanDataModel.cvx2

                            _cardInfoModelPos.value = cardModelIndex
                        }
                    }
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