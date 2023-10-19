package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.responses.Country
import net.pst.cash.data.responses.ErrorResponse
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val api: ApiService
) : CountriesRepository {
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    override suspend fun getCountriesList(): List<Country>? {
        return withContext(Dispatchers.IO) {
            try {
                val countriesResponse = api.getCountriesList()
                if (countriesResponse.isSuccessful) {
                    countriesResponse.body()?.data
                } else {
                    val errorBody = countriesResponse.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                    _errorMessage.postValue(errorResponse.message)
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue(e.message)
                null
            }
        }
    }
}