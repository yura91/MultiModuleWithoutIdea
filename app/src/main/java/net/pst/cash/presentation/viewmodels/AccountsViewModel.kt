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
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val application: Application,
    private val accountsInteractor: AccountsInteractor,
) : AndroidViewModel(application) {
    private val _addresses = MutableLiveData<List<String>>()
    val addresses: LiveData<List<String>>
        get() = _addresses

    init {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val addresses = accountsInteractor.getAccounts("Bearer $token")
            _addresses.value = addresses
        }
    }
}