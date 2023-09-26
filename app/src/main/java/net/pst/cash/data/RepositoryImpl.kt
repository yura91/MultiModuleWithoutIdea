package net.pst.cash.data

import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: ApiService) : Repository {
    override fun googleSignIn(googleToken: String): String {
        val signInResponse = api.signInGoogle(GoogleSignInRequest(googleToken))
        return signInResponse.token
    }
}