package net.pst.cash.domain

import androidx.lifecycle.MutableLiveData
import net.pst.cash.data.repos.ConfigRepo
import net.pst.cash.domain.model.ConfigData
import net.pst.cash.domain.model.Tariff
import javax.inject.Inject

class ConfigInteractorImpl @Inject constructor(private val configRepo: ConfigRepo) :
    ConfigInteractor {

    private val _configData = MutableLiveData<ConfigData>()
    override val configData = _configData

    override suspend fun getConfig() {
        val configResponse = configRepo.getConfig()
        val registerHash = configResponse?.registerHash
        val tarriffs = arrayListOf<Tariff>()
        val sortedTariffs = configResponse?.tariffs?.sortedBy { it.balance }

        sortedTariffs?.forEach { tariff ->
            val tariffData = Tariff(tariff.price, tariff.balance)
            tarriffs.add(tariffData)
        }
        val configData = ConfigData(registerHash, tarriffs)
        _configData.value = configData
    }
}