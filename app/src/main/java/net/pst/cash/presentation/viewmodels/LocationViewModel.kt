package net.pst.cash.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.CountriesListInteractor
import net.pst.cash.domain.VerificationInteractor
import net.pst.cash.domain.model.CountryModel
import net.pst.cash.presentation.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val countriesInteractor: CountriesListInteractor,
    private val verificationInteractor: VerificationInteractor
) :
    ViewModel() {

    var selectedItem: CountryModel? = null
    private val _countriesList = MutableLiveData<List<CountryModel>>()
    val countriesList: LiveData<List<CountryModel>> get() = _countriesList
    var firstName = ""
    var lastName = ""
    var birthDate = ""
    private val _verified = SingleLiveEvent<Unit?>()
    val verified: LiveData<Unit?> get() = _verified

    init {
        viewModelScope.launch {
            val countriesList = countriesInteractor.getCountriesList()
            countriesList?.let {
                _countriesList.value = it
            }
        }
    }

    fun verifyUser(
        token: String,
    ) {
        viewModelScope.launch {
            val idVerified = verificationInteractor.verifyUser(
                token,
                firstName,
                lastName,
                birthDate,
                selectedItem
            )
            if (idVerified == true) {
                _verified.call()
            }
        }
    }
}