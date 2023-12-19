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
import net.pst.cash.domain.UserInfoInteractor
import net.pst.cash.domain.VerificationInteractor
import net.pst.cash.presentation.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val application: Application,
    private val userInfoInteractor: UserInfoInteractor,
    private val verificationInteractor: VerificationInteractor,
    private val configInteractor: ConfigInteractor
) : AndroidViewModel(application) {

    val userInfoError: LiveData<String> = userInfoInteractor.errorMessage

    val navigateToLoginScreen: LiveData<Unit>
        get() = _navigateToLoginScreen

    private val _navigateToLoginScreen = SingleLiveEvent<Unit>()

    val navigateToReadyScreen: LiveData<Unit>
        get() = _navigateToReadyScreen

    private val _navigateToReadyScreen = SingleLiveEvent<Unit>()

    val navigateToGetAcquaintedScreen: LiveData<Unit>
        get() = _navigateToGetAcquaintedScreen

    private val _navigateToGetAcquaintedScreen = SingleLiveEvent<Unit>()

    val navigateToCardPaletteScreen: LiveData<Unit>
        get() = _navigateToCardPaletteScreen

    private val _navigateToCardPaletteScreen = SingleLiveEvent<Unit>()

    init {
        viewModelScope.launch {
            configInteractor.getConfig()
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            token?.let {
                val isSuccessFul = userInfoInteractor.getUserinfo("Bearer $it")
                if (isSuccessFul) {
                    checkVerificationNeed()
                } else {
                    _navigateToLoginScreen.value = Unit
                }
            }
        }
    }

    private fun checkVerificationNeed() {
        val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "")
        viewModelScope.launch {
            token?.let {
                val isVerificationNeeded = verificationInteractor.isVerificationNeeded("Bearer $it")
                val userId = sharedPref.getString("userId", "")
                if (isVerificationNeeded == false) {
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
                } else if (isVerificationNeeded == true) {
                    _navigateToGetAcquaintedScreen.value = Unit
                }
            }
        }
    }

    companion object {
        const val defColorValue = -1
    }
}