package net.pst.cash.domain

import net.pst.cash.data.CountriesRepository
import net.pst.cash.domain.model.CountryModel
import javax.inject.Inject

class CountriesListInteractorImpl @Inject constructor(private val countriesRepository: CountriesRepository) :
    CountriesListInteractor {
    override suspend fun getCountriesList(): List<CountryModel>? {
        val countriesList = countriesRepository.getCountriesList()
        if (countriesList != null) {
            val countriesModels = mutableListOf<CountryModel>()
            countriesList.forEach { country ->
                country.title?.let {
                    countriesModels.add(CountryModel(country.id, it))
                }
            }
            return countriesModels
        }
        return null
    }
}