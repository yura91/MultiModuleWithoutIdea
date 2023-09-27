package net.pst.cash.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.SignInInteractor
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(private val interactor: SignInInteractor) : ViewModel() {

    fun sendTokenToBackend(code: String?) {
        viewModelScope.launch {
            interactor.signInApple(code)
        }
    }
}