package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import net.pst.cash.domain.AccountsInteractor
import net.pst.cash.domain.ActiveCardInteractor
import net.pst.cash.domain.CardInfoInteractor
import net.pst.cash.domain.HistoryInteractor
import net.pst.cash.presentation.SingleLiveEvent
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

    var newCardId: Int = -1

    private val _cardList = MutableLiveData<List<CardModel>?>()
    val cardList: LiveData<List<CardModel>?>
        get() = _cardList

    private val _cardHistoriesList = MutableLiveData<List<CardModel>?>()
    val cardHistoriesList: LiveData<List<CardModel>?>
        get() = _cardHistoriesList

    private val _cardInfoModelPos = MutableLiveData<Int?>()
    val cardInfoModelPos: LiveData<Int?>
        get() = _cardInfoModelPos

    private val _deleteCardPos = MutableLiveData<Int?>()
    val deleteCardPos: LiveData<Int?>
        get() = _deleteCardPos

    private val _navigateToLoginScreen = SingleLiveEvent<Unit>()
    val navigateToLoginScreen: LiveData<Unit>
        get() = _navigateToLoginScreen

    val errorLoadCardList = activeCardInteractor.errorMessage

    private val _account = accountsInteractor.account.map {
        it?.balance?.split(" ")?.get(0)
    }
    val account: LiveData<String?>
        get() = _account

    private var jobAllCards: Job = Job()
    private var jobAllHistories: Job = Job()
    private var jobCardInfo: Job = Job()
    private var jobUpdateCardHistory: Job = Job()
    private var jobUpdateCard: Job = Job()
    private var jobDeleteCard: Job = Job()
    private var jobActiveBalance: Job = Job()

    fun getAllCards() {
        jobAllCards = viewModelScope.launch {
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
                        cardData.accountId,
                        currencyType,
                        cardData.balance,
                        lastCardDigits = cardData.lastFourDigits
                    )
                    cards.add(card)
                }
            }
            cards.add(CardModel())
            val newCardModelIndex = cards.indexOfFirst {
                it.id == newCardId
            }
            if (newCardModelIndex != -1) {
                val newCardModel = cards.removeAt(newCardModelIndex)
                newCardModel.let {
                    cards.add(0, it)
                }
            }
            _cardList.value = cards
        }
    }

    fun navigateToLogin() {
        cancelAllJobs()
        _navigateToLoginScreen.value = Unit
    }

    fun getAllCardHistories() {
        jobAllHistories = viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val cardList = cardList.value
            if (!cardList.isNullOrEmpty()) {
                cardList.subList(0, cardList.size - 1).forEachIndexed { index, cardModel ->
                    val cardId = cardModel.id.toString()
                    if (token != null) {
                        historyInteractor.getShortHistory("Bearer $token", cardId)
                            .flowOn(Dispatchers.IO)
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
                                _cardInfoModelPos.value = index
                            }
                    }
                }
            }
        }
    }

    fun getCardInfo(cardId: Int?) {
        jobCardInfo = viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val cardList = cardList.value
            val cardModelIndex = cardList?.indexOfFirst {
                it.id == cardId
            }
            if (cardModelIndex != -1) {
                cardModelIndex?.let { index ->
                    cardList[index].let {
                        token?.let { token ->
                            if (it.fullCardNumber == null && it.cvv == null && it.expireDate == null) {
                                val showPanDataModel =
                                    cardInfoInteractor.getCardInfo(
                                        "Bearer $token",
                                        cardId.toString()
                                    )
                                it.fullCardNumber = showPanDataModel.number
                                val expMonth = showPanDataModel.expMonth
                                val expYear = showPanDataModel.expYear
                                it.expireDate = "$expMonth/$expYear"
                                it.cvv = showPanDataModel.cvx2

                                _cardInfoModelPos.value = index
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateCardHistory(cardId: Int?) {
        jobUpdateCardHistory = viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val cardList = cardList.value
            val cardModelIndex = cardList?.indexOfFirst {
                it.id == cardId
            }
            if (cardModelIndex != -1) {
                cardModelIndex?.let { index ->
                    historyInteractor.getShortHistory("Bearer $token", cardId.toString())
                        .flowOn(Dispatchers.IO)
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
                            cardList[index].rowHistoryItems.clear()
                            cardList[index].rowHistoryItems.addAll(payments)
                            _cardInfoModelPos.value = index
                        }
                }
            }
        }
    }

    private fun cancelAllJobs() {
        jobActiveBalance.cancel()
        jobDeleteCard.cancel()
        jobUpdateCard.cancel()
        jobCardInfo.cancel()
        jobAllCards.cancel()
        jobAllHistories.cancel()
        jobUpdateCardHistory.cancel()

        _cardList.value = null
        _cardHistoriesList.value = null
        _cardInfoModelPos.value = null
        _deleteCardPos.value = null
        activeCardInteractor.clearErrors()
    }

    fun updateCard(cardId: Int?) {
        jobUpdateCard = viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val cardItem = activeCardInteractor.updateCard("Bearer $token", cardId.toString())
            val currencyType: String = when (cardItem?.currencyId) {
                Currency.DOLAR.currencyCode -> "$"
                Currency.EURO.currencyCode -> "€"
                else -> {
                    ""
                }
            }
            val cardList = cardList.value
            val card = CardModel(
                cardItem?.id,
                cardItem?.accountId,
                currencyType,
                cardItem?.balance,
                lastCardDigits = cardItem?.lastFourDigits
            )

            val cardModelIndex = cardList?.indexOfFirst {
                it.id == cardId
            }
            if (cardModelIndex != -1) {
                cardModelIndex?.let { index ->
                    cardList[index].let {
                        it.currencyType = card.currencyType
                        it.balance = card.balance
                        it.lastCardDigits = card.lastCardDigits
                    }
                    _cardInfoModelPos.value = index
                }
            }
        }
    }

    fun deleteCard(cardId: Int?, accountId: Int?) {
        jobDeleteCard = viewModelScope.launch {
            val cardList = cardList.value
            val cardModelIndex = cardList?.indexOfFirst {
                it.id == cardId
            }
            if (cardModelIndex != -1) {
                cardModelIndex?.let {
                    val sharedPref =
                        application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    val token = sharedPref.getString("token", "")
                    val success =
                        activeCardInteractor.deleteCard(
                            "Bearer $token",
                            cardId.toString(),
                            accountId
                        )
                    if (success) {
                        _deleteCardPos.value = it
                    }
                }
            }
        }
    }


    fun getActiveBalance() {
        jobActiveBalance = viewModelScope.launch {
            accountsInteractor.getAccounts()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}