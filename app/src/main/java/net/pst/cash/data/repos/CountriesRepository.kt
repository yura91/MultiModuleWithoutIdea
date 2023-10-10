package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import net.pst.cash.data.responses.Country

interface CountriesRepository {
    val errorMessage: LiveData<String>

    suspend fun getCountriesList(): List<Country>?
}