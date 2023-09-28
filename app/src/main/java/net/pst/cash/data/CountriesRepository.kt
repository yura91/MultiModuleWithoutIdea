package net.pst.cash.data

interface CountriesRepository {
    suspend fun getCountriesList(): List<Country>?
}