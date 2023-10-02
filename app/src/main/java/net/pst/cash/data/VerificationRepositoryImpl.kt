package net.pst.cash.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.pst.cash.domain.model.CountryModel
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor(
    private val api: ApiService
) : VerificationRepository {
    override suspend fun isVerificationNeeded(token: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val verActualResponse = api.isVerificationNeeded(token)
                if (verActualResponse.isSuccessful) {
                    val step = verActualResponse.body()?.data?.step
                    true
//                    step.isNullOrEmpty()
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
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
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }


}