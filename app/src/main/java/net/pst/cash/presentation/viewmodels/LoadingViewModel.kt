package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.ConfigInteractor
import net.pst.cash.domain.UserInfoInteractor
import net.pst.cash.domain.VerificationInteractor
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val application: Application,
    private val userInfoInteractor: UserInfoInteractor,
    private val verificationInteractor: VerificationInteractor,
    private val configInteractor: ConfigInteractor
) : AndroidViewModel(application) {

    val userInfoError: LiveData<String> = userInfoInteractor.errorMessage

    val navigateToLoginScreen: LiveData<Unit>
        get() = _navigateToLoginScreen

    private val _navigateToLoginScreen = MutableLiveData<Unit>()

    val navigateToGetAcquaintedScreen: LiveData<Unit>
        get() = _navigateToGetAcquaintedScreen

    private val _navigateToGetAcquaintedScreen = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            configInteractor.getConfig()
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            token?.let {
                val isSuccessFul = userInfoInteractor.getUserinfo("Bearer $it")
                if (isSuccessFul) {
                    checkVerificationNeed()
                } else {
                    _navigateToLoginScreen.value = Unit
                }
            }
        }
    }

    private fun checkVerificationNeed() {
        val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "")
        viewModelScope.launch {
            token?.let {
                val isVerificationNeeded = verificationInteractor.isVerificationNeeded("Bearer $it")
                if (isVerificationNeeded) {
                    _navigateToGetAcquaintedScreen.value = Unit
                }
            }
        }
    }
}