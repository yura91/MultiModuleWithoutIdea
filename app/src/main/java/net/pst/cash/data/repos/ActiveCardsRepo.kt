package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import net.pst.cash.data.responses.CardResponseData

interface ActiveCardsRepo {
    val errorMessage: LiveData<String>
    suspend fun getAllCards(token: String): List<CardResponseData>?

    suspend fun updateCard(token: String, cardId: String): CardResponseData?
}