package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.data.ApiService
import net.pst.cash.data.requests.VerificationRequest
import net.pst.cash.data.responses.ErrorResponse
import net.pst.cash.domain.model.CountryModel
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor(
    private val api: ApiService
) : VerificationRepository {
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    override suspend fun isVerificationNeeded(token: String): Boolean? {
        return withContext(Dispatchers.IO) {
            try {
                val verActualResponse = api.isVerificationActual(token)
                if (verActualResponse.isSuccessful) {
                    val step = verActualResponse.body()?.data?.step
                    step.isNullOrEmpty()
                } else {
                    val errorBody = verActualResponse.errorBody()?.string()
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

    override suspend fun verifyUser(
        token: String,
        firstName: String,
        lastName: String,
        birthDate: String,
        selectedItem: CountryModel?
    ): Boolean? {
        return withContext(Dispatchers.IO) {
            try {
                val verifyResponse = api.verifyUser(
                    token,
                    VerificationRequest(
                        firstName = firstName,
                        lastName = lastName,
                        birthday = birthDate,
                        countryId = selectedItem?.id.toString(),
                        actualCountryId = selectedItem?.id.toString()
                    )
                )
                if (verifyResponse.isSuccessful) {
                    verifyResponse.body()?.success
                } else {
                    val errorBody = verifyResponse.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                    _errorMessage.postValue(errorResponse.message)
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue(e.message)
                false
            }
        }
    }
}