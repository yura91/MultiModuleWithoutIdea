package net.pst.cash.domain

import net.pst.cash.data.repos.ActiveCardsRepo
import net.pst.cash.data.responses.CardResponseData
import net.pst.cash.domain.model.CardModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ActiveCardInteractorImpl @Inject constructor(private val activeCardsRepo: ActiveCardsRepo) :
    ActiveCardInteractor {
    override suspend fun getActiveCardModel(token: String): List<CardModel>? {
        val activeCardsResponse: List<CardResponseData>? = activeCardsRepo.checkActiveCard(token)
        return if (!activeCardsResponse.isNullOrEmpty()) {
            val cardModels: MutableList<CardModel> = mutableListOf()
            activeCardsResponse.forEach { cardDataModel ->
                val lastCardDigits = cardDataModel.mask?.let { it.substring(it.length - 4) }
                val formattedDate = convertDate(cardDataModel.orderedUntil)
                val cardModel = CardModel(
                    cardDataModel.id,
                    cardDataModel.account?.currencyId,
                    cardDataModel.account?.balance,
                    cardDataModel.holderName,
                    lastCardDigits,
                    formattedDate
                )
                cardModels.add(cardModel)
            }
            return cardModels
        } else {
            null
        }
    }

    private fun convertDate(inputDate: String?): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("MM/yy", Locale.ENGLISH)

        val date = inputFormat.parse(inputDate)
        val formattedDate = outputFormat.format(date)
        return formattedDate
    }
}