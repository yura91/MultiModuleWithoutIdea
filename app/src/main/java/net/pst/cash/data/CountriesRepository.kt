package net.pst.cash.data

import androidx.lifecycle.LiveData

interface CountriesRepository {
    val errorMessage: LiveData<String>

    suspend fun getCountriesList(): List<Country>?
}