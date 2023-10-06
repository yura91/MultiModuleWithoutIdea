package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.data.CountriesRepository
import net.pst.cash.domain.model.CountryModel
import javax.inject.Inject

class CountriesListInteractorImpl @Inject constructor(private val countriesRepository: CountriesRepository) :
    CountriesListInteractor {
    override val errorMessage: LiveData<String> = countriesRepository.errorMessage

    override suspend fun getCountriesList(): List<CountryModel>? {
        val countriesList = countriesRepository.getCountriesList()
        if (countriesList != null) {
            val countriesModels = mutableListOf<CountryModel>()
            countriesList.forEach { country ->
                country.title?.let {
                    countriesModels.add(CountryModel(country.id, it, country.iso2?.lowercase()))
                }
            }
            return countriesModels
        }
        return null
    }
}