package net.pst.cash.domain

import net.pst.cash.domain.model.CountryModel

interface CountriesListInteractor {
    suspend fun getCountriesList(): List<CountryModel>?
}