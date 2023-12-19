package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.AccountsInteractor
import net.pst.cash.presentation.model.CardModel
import javax.inject.Inject

@HiltViewModel
class IssueCardViewModel @Inject constructor(
    private val application: Application,
    private val accountsInteractor: AccountsInteractor
) : AndroidViewModel(application) {

    private val _cardModel = MutableLiveData<CardModel>()
    val cardModel: LiveData<CardModel>
        get() = _cardModel

    fun getActiveBalance() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val account = accountsInteractor.getAccounts("Bearer $token")

        }
    }

    fun getCardBalance(): String? = _cardModel.value?.balance

    fun getCurrency(): String? = _cardModel.value?.currencyType
}