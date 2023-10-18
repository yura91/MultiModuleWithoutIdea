package net.pst.cash.domain

import net.pst.cash.data.repos.CardIsReadyRepo
import net.pst.cash.data.responses.CardResponseData
import javax.inject.Inject

class CardIsReadyInteractorImpl @Inject constructor(private val cardIsReadyRepo: CardIsReadyRepo) :
    CardIsReadyInteractor {
    override suspend fun checkActiveCard(token: String): List<CardResponseData>? {
        return cardIsReadyRepo.checkActiveCard(token)
    }
}