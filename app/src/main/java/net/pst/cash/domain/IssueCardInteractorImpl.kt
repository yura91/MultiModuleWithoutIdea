package net.pst.cash.domain

import net.pst.cash.data.repos.IssueCardRepo
import net.pst.cash.data.responses.IssueCardResponse
import javax.inject.Inject

class IssueCardInteractorImpl @Inject constructor(private val issueCardRepo: IssueCardRepo) :
    IssueCardInteractor {
    override suspend fun issueCard(
        token: String,
        accountId: Int?,
        startBalance: String?
    ): IssueCardResponse? {
        return issueCardRepo.issueCard(token, accountId, startBalance)
    }
}