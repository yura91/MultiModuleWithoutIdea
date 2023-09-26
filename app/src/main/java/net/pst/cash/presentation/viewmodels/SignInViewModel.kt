package net.pst.cash.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import net.pst.cash.domain.SignInInteractor
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val interactor: SignInInteractor) : ViewModel() {

    fun sendTokenToBackend(googleToken: String) {
        interactor.googleSignIn(googleToken)
    }

}