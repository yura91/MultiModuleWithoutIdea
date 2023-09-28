package net.pst.cash.domain

interface CountriesListInteractor {
    suspend fun getCountriesList(): List<String>?
}