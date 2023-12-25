package net.pst.cash.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import net.pst.cash.data.repos.IssueCardRepo
import net.pst.cash.data.responses.IssueCardResponse
import net.pst.cash.domain.model.ErrorModel
import javax.inject.Inject

class IssueCardInteractorImpl @Inject constructor(private val issueCardRepo: IssueCardRepo) :
    IssueCardInteractor {
    override val errorModel: LiveData<ErrorModel> = issueCardRepo.errorResponse.map {
        ErrorModel(it.success, it.message, it.type)
    }

    override suspend fun issueCard(
        token: String,
        accountId: Int,
        cardBalance: String
    ): IssueCardResponse? {
        return issueCardRepo.issueCard(token, accountId, cardBalance)
    }
}