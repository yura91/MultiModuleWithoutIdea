package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.domain.model.ConfigData

interface ConfigInteractor {
    val configData: LiveData<ConfigData?>
    suspend fun getConfig()
}