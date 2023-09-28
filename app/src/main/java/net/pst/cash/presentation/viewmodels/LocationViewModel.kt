package net.pst.cash.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pst.cash.domain.CountriesListInteractor
import net.pst.cash.domain.model.CountryModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val interactor: CountriesListInteractor) :
    ViewModel() {
    var selectedItem: CountryModel? = null
    private val _countriesList = MutableLiveData<List<CountryModel>>()
    val countriesList: LiveData<List<CountryModel>> get() = _countriesList

    init {
        viewModelScope.launch {
            val countriesList = interactor.getCountriesList()
            countriesList?.let {
                _countriesList.value = it
            }
        }
    }
}