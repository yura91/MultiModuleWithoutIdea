package net.pst.cash.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

    fun signInEmail(email: String, password: String) {
        viewModelScope.launch {
            signInInteractor.signInEmail(email, password)
        }
    }
}