package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.ConfigInteractor
import net.pst.cash.domain.SignInInteractor
import net.pst.cash.domain.VerificationInteractor
import net.pst.cash.presentation.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class AppleWebViewViewModel @Inject constructor(
    private val application: Application,
    private val signInInteractor: SignInInteractor,
    private val configInteractor: ConfigInteractor,
    private val verifyInteractor: VerificationInteractor
) : AndroidViewModel(application) {
    private val _navigateToGetAquintedScreen = SingleLiveEvent<Unit?>()
    val navigateToGetAquintedScreen = _navigateToGetAquintedScreen
    val snackBarErrorMessage = signInInteractor.errorMessage
    val configData = configInteractor.configData
    val navigateToReadyScreen: LiveData<Unit>
        get() = _navigateToReadyScreen

    private val _navigateToReadyScreen = SingleLiveEvent<Unit>()
    var registerHash: String? = null
    fun sendAppleCodeToBackend(code: String?) {
        viewModelScope.launch {
            val isAppleSuccess = signInInteractor.signInApple(code, registerHash)
            if (isAppleSuccess) {
                val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val token = sharedPref.getString("token", "")

                val isVerificationNeeded = verifyInteractor.isVerificationNeeded("Bearer $token")
                if (isVerificationNeeded == true) {
                    _navigateToGetAquintedScreen.call()
                } else if (isVerificationNeeded == false) {
                    _navigateToReadyScreen.call()
                }
            }
        }
    }
}