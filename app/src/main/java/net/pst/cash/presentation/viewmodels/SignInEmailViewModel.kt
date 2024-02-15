package net.pst.cash.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.SignInInteractor
import javax.inject.Inject

@HiltViewModel
class SignInEmailViewModel @Inject constructor(
    private val application: Application,
    private val signInInteractor: SignInInteractor,
) : AndroidViewModel(application) {
    val snackBarErrorMessage = signInInteractor.errorMessage

    val emptyEmailLiveData: LiveData<Unit>
        get() = _emptyEmailLiveData
    private val _emptyEmailLiveData = MutableLiveData<Unit>()

    val emptyPasswordLiveData: LiveData<Unit>
        get() = _emptyPasswordLiveData
    private val _emptyPasswordLiveData = MutableLiveData<Unit>()

    val sendSignInReqLiveData: LiveData<EmailAndPassword>
        get() = _sendSignInReqLiveData
    private val _sendSignInReqLiveData = MutableLiveData<EmailAndPassword>()

    fun signInEmail(email: String, password: String) {
        viewModelScope.launch {
            signInInteractor.signInEmail(email, password)
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

