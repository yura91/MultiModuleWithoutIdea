package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.domain.model.CardModel

interface ActiveCardInteractor {
    val errorMessage: LiveData<String>
    suspend fun getActiveCardModel(token: String): List<CardModel>?
}