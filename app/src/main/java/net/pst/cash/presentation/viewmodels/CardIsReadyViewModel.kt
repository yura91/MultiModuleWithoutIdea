package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.ActiveCardInteractor
import net.pst.cash.presentation.model.CardModel
import net.pst.cash.presentation.model.Сurrency
import javax.inject.Inject

@HiltViewModel
class CardIsReadyViewModel @Inject constructor(
    private val application: Application,
    private val activeCardInteractor: ActiveCardInteractor
) : AndroidViewModel(application) {
    private val _cardModel = MutableLiveData<CardModel>()
    val cardModel: LiveData<CardModel>
        get() = _cardModel

    fun checkActiveCards() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val activeCard = activeCardInteractor.getActiveCardModel("Bearer $token")
            activeCard?.let {
                val currencyType: String = when (it.currencyId) {
                    Сurrency.DOLAR.currencyCode -> "$"
                    Сurrency.EURO.currencyCode -> "€"
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