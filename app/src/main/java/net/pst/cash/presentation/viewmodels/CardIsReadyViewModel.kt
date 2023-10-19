package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.CardIsReadyInteractor
import javax.inject.Inject

@HiltViewModel
class CardIsReadyViewModel @Inject constructor(
    private val application: Application,
    private val cardIsReadyInteractor: CardIsReadyInteractor
) : AndroidViewModel(application) {
    private val _cardId = MutableLiveData<Int>()
    val cardId: LiveData<Int>
        get() = _cardId

    fun checkActiveCards() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val activeCardId = cardIsReadyInteractor.getActiveCardId("Bearer $token")
            activeCardId?.let {
                _cardId.value = it
            }
        }
    }
}