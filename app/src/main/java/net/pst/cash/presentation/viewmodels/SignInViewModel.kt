package net.pst.cash.presentation.viewmodels

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.R
import net.pst.cash.domain.SignInInteractor
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    application: Application,
    private val interactor: SignInInteractor
) : AndroidViewModel(application) {
    private val oneTapClient = Identity.getSignInClient(application)
    private var signUpRequest: BeginSignInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(application.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    private val _signInRequest = MutableLiveData<IntentSenderRequest>()
    val signInRequest: LiveData<IntentSenderRequest> get() = _signInRequest

    private val _appleLink = MutableLiveData<String?>()
    val appleLink: LiveData<String?> get() = _appleLink

    fun startSignIn() {
        oneTapClient.beginSignIn(signUpRequest)
            .addOnSuccessListener { result ->
                val intentSenderRequest: IntentSenderRequest =
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                _signInRequest.value = intentSenderRequest
            }
            .addOnFailureListener { e ->
                e.localizedMessage?.let { Log.d("TAG", it) }
            }
    }

    private fun sendTokenToBackend(googleToken: String) {
        viewModelScope.launch {
            interactor.signInGoogle(googleToken)
        }
    }

    fun getAppleLink() {
        viewModelScope.launch {
            val link: String? = interactor.getAppleLink()
            _appleLink.value = link
        }
    }

    fun clearAppleLink() {
        _appleLink.value = null
    }

    fun handleSignInResult(result: ActivityResult?) {
        if (result?.resultCode == Activity.RESULT_OK) {
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                when {
                    idToken != null -> {
                        sendTokenToBackend(idToken)
                    }

                    else -> {
                    }
                }
            } catch (e: ApiException) {
            }
        }
    }
}