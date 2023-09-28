package net.pst.cash.domain

import net.pst.cash.data.CountriesRepository
import javax.inject.Inject

class CountriesListInteractorImpl @Inject constructor(private val countriesRepository: CountriesRepository) :
    CountriesListInteractor {
    override suspend fun getCountriesList(): List<String>? {
        val countriesList = countriesRepository.getCountriesList()
        if (countriesList != null) {
            val countriesTittles = mutableListOf<String>()
            countriesList?.forEach { country ->
                country.title?.let {
                    countriesTittles.add(it)
                }
            }
            return countriesTittles
        }
        return null
    }
}