package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.CardInfoInteractor
import javax.inject.Inject

@HiltViewModel
class CardInfoViewModel @Inject constructor(
    private val application: Application,
    private val cardInfoInteractor: CardInfoInteractor
) : AndroidViewModel(application) {

    fun getCardInfo(cardId: String) {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            cardInfoInteractor.getCardInfo("Bearer $token", cardId)
        }
    }
}