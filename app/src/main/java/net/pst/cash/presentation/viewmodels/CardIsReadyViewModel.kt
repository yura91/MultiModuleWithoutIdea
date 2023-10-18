package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.CardIsReadyInteractor
import javax.inject.Inject

@HiltViewModel
class CardIsReadyViewModel @Inject constructor(
    private val application: Application,
    cardIsReadyInteractor: CardIsReadyInteractor
) : AndroidViewModel(application) {

    init {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            cardIsReadyInteractor.checkActiveCard("Bearer $token")
        }
    }
}