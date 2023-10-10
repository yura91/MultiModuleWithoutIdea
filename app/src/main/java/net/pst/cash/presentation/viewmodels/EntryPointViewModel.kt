package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.UserInfoInteractor
import javax.inject.Inject

@HiltViewModel
class EntryPointViewModel @Inject constructor(
    private val application: Application,
    userInfoInteractor: UserInfoInteractor
) : AndroidViewModel(application) {

    val userInfoError: LiveData<String> = userInfoInteractor.errorMessage

    val successUserInfo: LiveData<Boolean>
        get() = _successUserInfo

    private val _successUserInfo = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            token?.let {
                val isSuccessFul = userInfoInteractor.getUserinfo("Bearer $it")
                _successUserInfo.value = isSuccessFul
            }
        }
    }
}