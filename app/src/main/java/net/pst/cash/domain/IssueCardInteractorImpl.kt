package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.data.repos.IssueCardRepo
import net.pst.cash.data.responses.IssueCardResponse
import javax.inject.Inject

class IssueCardInteractorImpl @Inject constructor(private val issueCardRepo: IssueCardRepo) :
    IssueCardInteractor {
    override val errorMessage: LiveData<String> = issueCardRepo.errorMessage
    override suspend fun issueCard(
        token: String,
        accountId: Int,
        cardBalance: String
    ): IssueCardResponse? {
        return issueCardRepo.issueCard(token, accountId, cardBalance)
    }
}