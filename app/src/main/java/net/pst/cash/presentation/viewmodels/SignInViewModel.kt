package net.pst.cash.presentation.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Context
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
import net.pst.cash.domain.ConfigInteractor
import net.pst.cash.domain.SignInInteractor
import net.pst.cash.domain.VerificationInteractor
import net.pst.cash.presentation.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val application: Application,
    private val signInInteractor: SignInInteractor,
    private val verifyInteractor: VerificationInteractor,
    private val configInteractor: ConfigInteractor
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

    private val _navigateToGetAcquaintedScreen = SingleLiveEvent<Boolean>()
    val navigateToGetAcquaintedScreen = _navigateToGetAcquaintedScreen

    val navigateToCardPaletteScreen: LiveData<Unit>
        get() = _navigateToCardPaletteScreen

    private val _navigateToCardPaletteScreen = SingleLiveEvent<Unit>()

    val navigateToReadyScreen: LiveData<Unit>
        get() = _navigateToReadyScreen

    private val _navigateToReadyScreen = SingleLiveEvent<Unit>()

    private val _signInGoogleRequest = MutableLiveData<IntentSenderRequest>()
    val signInGoogleRequest: LiveData<IntentSenderRequest> get() = _signInGoogleRequest

    private val _appleLink = SingleLiveEvent<String>()
    val appleLink: LiveData<String> get() = _appleLink

    var registerHash: String? = null

    val snackBarErrorMessage = signInInteractor.errorMessage

    val configData = configInteractor.configData

    fun signInWithGoogle() {
        oneTapClient.beginSignIn(signUpRequest)
            .addOnSuccessListener { result ->
                val intentSenderRequest: IntentSenderRequest =
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                _signInGoogleRequest.value = intentSenderRequest
            }
            .addOnFailureListener { e ->
                e.localizedMessage?.let { Log.d("TAG", it) }
            }
    }

    private fun sendGoogleTokenToBackend(registerHash: String, googleToken: String) {
        viewModelScope.launch {
            val isGoogleSuccess = signInInteractor.signInGoogle(registerHash, googleToken)
            if (isGoogleSuccess) {
                val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val token = sharedPref.getString("token", "")
                val userId = sharedPref.getString(application.getString(R.string.userid), "")
                val isVerificationNeeded = verifyInteractor.isVerificationNeeded("Bearer $token")
                if (isVerificationNeeded == true) {
                    _navigateToGetAcquaintedScreen.value = true
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

    fun getAppleLink() {
        viewModelScope.launch {
            val link: String? = signInInteractor.getAppleLink()
            link?.let {
                _appleLink.value = it
            }
        }
    }

    fun handleSignInGoogleResult(result: ActivityResult?) {
        if (result?.resultCode == Activity.RESULT_OK) {
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                when {
                    idToken != null -> {
                        registerHash?.let {
                            sendGoogleTokenToBackend(it, idToken)
                        }
                    }

                    else -> {
                    }
                }
            } catch (e: ApiException) {
            }
        }
    }

    companion object {
        const val defColorValue = -1
    }
}