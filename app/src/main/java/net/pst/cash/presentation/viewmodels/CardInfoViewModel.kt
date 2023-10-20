package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.CardInfoInteractor
import net.pst.cash.domain.model.ShowPanDataModel
import javax.inject.Inject

@HiltViewModel
class CardInfoViewModel @Inject constructor(
    private val application: Application,
    private val cardInfoInteractor: CardInfoInteractor
) : AndroidViewModel(application) {
    val cardInfoData
        get() = _cardInfoData
    private val _cardInfoData = MutableLiveData<ShowPanDataModel>()
    fun getCardInfo(cardId: String) {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val cardInfo = cardInfoInteractor.getCardInfo("Bearer $token", cardId)
            _cardInfoData.value = cardInfo
        }
    }
}