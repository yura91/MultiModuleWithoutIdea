package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.SignInInteractor
import net.pst.cash.domain.VerificationInteractor
import net.pst.cash.presentation.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class AppleWebViewViewModel @Inject constructor(
    private val application: Application,
    private val signInInteractor: SignInInteractor,
    private val verifyInteractor: VerificationInteractor
) : AndroidViewModel(application) {
    private val _isVerificationNeeded = SingleLiveEvent<Unit?>()
    val isVerificationNeeded = _isVerificationNeeded
    val snackBarErrorMessage = signInInteractor.errorMessage

    fun sendAppleCodeToBackend(code: String?) {
        viewModelScope.launch {
            val isAppleSuccess = signInInteractor.signInApple(code)
            if (isAppleSuccess) {
                val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val token = sharedPref.getString("token", "")

                val isVerificationNeeded = verifyInteractor.isVerificationNeeded("Bearer $token")
                if (isVerificationNeeded) {
                    _isVerificationNeeded.call()
                }
            }
        }
    }
}