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
) : ViewModel() {
    val snackBarVerificationErrorMessage = verificationInteractor.errorMessage
    val snackBarCountriesErrorMessage = countriesInteractor.errorMessage
    private val bannedCountries = listOf(
        "Afghanistan",
        "Belarus",
        "Burundi",
        "Central African",
        "Cuba",
        "Republic of the Congo",
        "The Democratic Republic Of The Congo",
        "Côte d`Ivoire",
        "Hong Kong",
        "Islamic Republic of Iran",
        "Iraq",
        "Lebanon",
        "Liberia",
        "Libyan Arab Jamahiriya",
        "Myanmar",
        "Nicaragua",
        "Democratic People's Republic of Korea",
        "Russian Federation",
        "Somalia",
        "Sudan",
        "South Sudan",
        "Syrian Arab Republic",
        "Ukraine",
        "United States",
        "Venezuela",
        "Yemen",
        "Zimbabwe",
    )
    var selectedItem: CountryModel? = null
        set(country) {
            field = country
            _banned.value = bannedCountries.contains(country?.title)
        }
    private val _countriesList = MutableLiveData<List<CountryModel>>()
    val countriesList: LiveData<List<CountryModel>> get() = _countriesList
    var firstName = ""
    var lastName = ""
    var birthDate = ""
    private val _verified = SingleLiveEvent<Unit?>()
    val verified: LiveData<Unit?> get() = _verified

    private val _banned = MutableLiveData<Boolean>()
    val banned: LiveData<Boolean> get() = _banned

    init {
        viewModelScope.launch {
            val countriesList = countriesInteractor.getCountriesList()
            countriesList?.let {
                _countriesList.value = it
            }
        }
    }

    fun goNext(token: String) {
            verifyUser(token)
    }

    private fun verifyUser(
        token: String
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