package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.domain.model.CountryModel

interface CountriesListInteractor {
    val errorMessage: LiveData<String>

    suspend fun getCountriesList(): List<CountryModel>?
}