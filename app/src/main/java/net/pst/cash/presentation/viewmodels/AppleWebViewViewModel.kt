package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.R
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

    val navigateToCardPaletteScreen: LiveData<Unit>
        get() = _navigateToCardPaletteScreen

    private val _navigateToCardPaletteScreen = SingleLiveEvent<Unit>()

    private val _navigateToReadyScreen = SingleLiveEvent<Unit>()
    var registerHash: String? = null
    fun sendAppleCodeToBackend(code: String?) {
        viewModelScope.launch {
            val isAppleSuccess = signInInteractor.signInApple(code, registerHash)
            if (isAppleSuccess) {
                val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val token = sharedPref.getString("token", "")
                val userId = sharedPref.getString(application.getString(R.string.userid), "")
                val isVerificationNeeded = verifyInteractor.isVerificationNeeded("Bearer $token")
                if (isVerificationNeeded == true) {
                    _navigateToGetAquintedScreen.call()
                } else if (isVerificationNeeded == false) {
                    val startColor = sharedPref.getInt(
                        userId + application.getString(R.string.startcolor),
                        defColorValue
                    )
                    val endColor = sharedPref.getInt(
                        userId + application.getString(R.string.endcolor),
                        defColorValue
                    )
                    if (startColor != defColorValue && endColor != defColorValue) {
                        _navigateToReadyScreen.value = Unit
                    } else {
                        _navigateToCardPaletteScreen.value = Unit
                    }
                }
            }
        }
    }

    companion object {
        const val defColorValue = -1
    }
}