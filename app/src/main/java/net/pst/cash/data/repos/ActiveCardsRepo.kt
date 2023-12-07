package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import net.pst.cash.data.responses.CardResponseData

interface ActiveCardsRepo {
    val errorMessage: LiveData<String>
    suspend fun checkActiveCard(token: String): List<CardResponseData>?
}