package net.pst.cash.data.repos

import net.pst.cash.data.ApiService
import net.pst.cash.data.requests.IssueCardRequest
import net.pst.cash.data.responses.IssueCardResponse
import javax.inject.Inject

class IssueCardRepoImpl @Inject constructor(
    private val api: ApiService
) : IssueCardRepo {
    override suspend fun issueCard(
        token: String,
        accountId: String?,
        startBalance: String?
    ): IssueCardResponse? {
        val issueCardRequest = IssueCardRequest(accountId = accountId, startBalance = startBalance)
        return api.issueCard(token, issueCardRequest).body()
    }
}