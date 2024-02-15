package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.R
import net.pst.cash.domain.SignInInteractor
import net.pst.cash.domain.VerificationInteractor
import net.pst.cash.presentation.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class SignInEmailViewModel @Inject constructor(
    private val application: Application,
    private val verifyInteractor: VerificationInteractor,
    private val signInInteractor: SignInInteractor,
) : AndroidViewModel(application) {
    val snackBarErrorMessage = signInInteractor.errorMessage

    val emptyEmailLiveData: LiveData<Unit>
        get() = _emptyEmailLiveData
    private val _emptyEmailLiveData = MutableLiveData<Unit>()

    val emptyPasswordLiveData: LiveData<Unit>
        get() = _emptyPasswordLiveData
    private val _emptyPasswordLiveData = MutableLiveData<Unit>()

    private val _navigateToGetAcquaintedScreen = SingleLiveEvent<Boolean>()
    val navigateToGetAcquaintedScreen = _navigateToGetAcquaintedScreen

    val navigateToCardPaletteScreen: LiveData<Unit>
        get() = _navigateToCardPaletteScreen

    val navigateToReadyScreen: LiveData<Unit>
        get() = _navigateToReadyScreen

    private val _navigateToReadyScreen = SingleLiveEvent<Unit>()

    private val _navigateToCardPaletteScreen = SingleLiveEvent<Unit>()

    val sendSignInReqLiveData: LiveData<EmailAndPassword>
        get() = _sendSignInReqLiveData
    private val _sendSignInReqLiveData = MutableLiveData<EmailAndPassword>()

    fun signInEmail(email: String, password: String) {
        viewModelScope.launch {
            val isSignInResultSuccess = signInInteractor.signInEmail(email, password)
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val userId = sharedPref.getString(application.getString(R.string.userid), "")
            if (isSignInResultSuccess) {
                val isVerificationNeeded = verifyInteractor.isVerificationNeeded("Bearer $token")
                if (isVerificationNeeded == true) {
                    _navigateToGetAcquaintedScreen.value = true
                } else if (isVerificationNeeded == false) {
                    val startColor = sharedPref.getInt(
                        userId + application.getString(R.string.startcolor),
                        SignInViewModel.defColorValue
                    )
                    val endColor = sharedPref.getInt(
                        userId + application.getString(R.string.endcolor),
                        SignInViewModel.defColorValue
                    )
                    if (startColor != SignInViewModel.defColorValue && endColor != SignInViewModel.defColorValue) {
                        _navigateToReadyScreen.value = Unit
                    } else {
                        _navigateToCardPaletteScreen.value = Unit
                    }
                }
            }
        }
    }

    fun validateForm(email: String, password: String) {
        if (email.isEmpty()) {
            _emptyEmailLiveData.value = Unit
        } else if (password.isEmpty()) {
            _emptyPasswordLiveData.value = Unit
        } else {
            _sendSignInReqLiveData.value = EmailAndPassword(email, password)
        }
    }
}

data class EmailAndPassword(val email: String, val password: String)

