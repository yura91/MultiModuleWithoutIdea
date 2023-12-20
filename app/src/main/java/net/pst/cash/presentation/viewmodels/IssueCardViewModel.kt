package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.AccountsInteractor
import javax.inject.Inject

@HiltViewModel
class IssueCardViewModel @Inject constructor(
    private val application: Application,
    private val accountsInteractor: AccountsInteractor
) : AndroidViewModel(application) {

    private val _account = accountsInteractor.account.map {
        it?.balance
    }
    val account: LiveData<String?>
        get() = _account

    fun getActiveBalance() {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            accountsInteractor.getAccounts("Bearer $token")
        }
    }
}