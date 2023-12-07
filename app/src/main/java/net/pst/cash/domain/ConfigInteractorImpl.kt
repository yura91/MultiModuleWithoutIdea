package net.pst.cash.domain

import androidx.lifecycle.MutableLiveData
import net.pst.cash.data.repos.ConfigRepoImpl
import net.pst.cash.domain.model.ConfigData
import net.pst.cash.domain.model.Tariff
import javax.inject.Inject

class ConfigInteractorImpl @Inject constructor(private val configRepo: ConfigRepoImpl) :
    ConfigInteractor {

    private val _configData = MutableLiveData<ConfigData>()
    override val configData = _configData

    override suspend fun getConfig() {
        val configResponse = configRepo.getConfig()
        val registerHash = configResponse?.registerHash
        val tarriffs = arrayListOf<Tariff>()
        configResponse?.tariffs?.forEach { tariff ->
            val tariffData = Tariff(tariff.price, tariff.balance)
            tarriffs.add(tariffData)
        }
        val configData = ConfigData(registerHash, tarriffs)
        _configData.value = configData
    }
}