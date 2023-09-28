package net.pst.cash.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val api: ApiService
) : CountriesRepository {
    override suspend fun getCountriesList(): List<Country>? {
        return withContext(Dispatchers.IO) {
            try {
                val countriesResponse = api.getCountriesList()
                if (countriesResponse.isSuccessful) {
                    countriesResponse.body()?.data
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}