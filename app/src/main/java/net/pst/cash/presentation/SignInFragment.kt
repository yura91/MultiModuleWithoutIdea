package net.pst.cash.presentation

import android.app.Activity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentSignInBinding
import net.pst.cash.presentation.model.getClickableSpan
import net.pst.cash.presentation.viewmodels.SignInViewModel

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {
    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest

    private val signInViewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val agreementSpan = SpannableStringBuilder()
        agreementSpan.append(getPrivacySpan())
        agreementSpan.append(getTermsSpan())
        binding?.textPrivacyPolicy?.text = agreementSpan
        binding?.textPrivacyPolicy?.movementMethod = LinkMovementMethod.getInstance()
        oneTapClient = Identity.getSignInClient(requireActivity())
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.web_client_id))
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
            registerForActivityResult(
                ActivityResultContracts.StartIntentSenderForResult()
            ) { result ->
                if (result?.resultCode == Activity.RESULT_OK) {
                    try {
                        val credential =
                            oneTapClient.getSignInCredentialFromIntent(result.data)
                        val idToken = credential.googleIdToken
                        when {
                            idToken != null -> {

                                val email = credential.id
                                Toast.makeText(
                                    requireContext(),
                                    "Email: " + email,
                                    Toast.LENGTH_SHORT
                                ).show()

                                signInViewModel.sendTokenToBackend(idToken)
                                // Got an ID token from Google. Use it to authenticate
                                // with your backend.
                                //                                         Log.d(TAG, "Got ID token.")
                            }

                            else -> {
                                // Shouldn't happen.
                                //                                         Log.d(TAG, "No ID token!")
                            }
                        }
                    } catch (e: ApiException) {
                        // ...
                        e.printStackTrace()
                    }

                }
            }

        binding?.signWithGoogle?.setOnClickListener {
            /*oneTapClient.beginSignIn(signUpRequest)
                .addOnSuccessListener(requireActivity()) { result ->
                    val intentSenderRequest: IntentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    activityResultLauncher.launch(intentSenderRequest)
                }
                .addOnFailureListener(requireActivity()) { e ->
                    // No Google Accounts found. Just continue presenting the signed-out UI.
                    Log.d("TAG", e.localizedMessage)
                }*/
            signInViewModel.sendTokenToBackend("eyJhbGciOiJSUzI1NiIsImtpZCI6ImI5YWM2MDFkMTMxZmQ0ZmZkNTU2ZmYwMzJhYWIxODg4ODBjZGUzYjkiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIzNjQyOTAyMDk2MjktcDdicHMzZTJ2c2JldTNwYWN0YmYwNGRtZDZzbTVqc3EuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIzNjQyOTAyMDk2MjktMmkxb2Y2dTk5bzM2dm5hM3FydmFiajJhM2tqdmhtMjguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDEwNDIwNzQ1Mzc3MzA4Njc2MzYiLCJlbWFpbCI6InVyaWp2bGFzaWtAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlhdCI6MTY5NTczMjI1MCwiZXhwIjoxNjk1NzM1ODUwfQ.f61uc7vxWH7uI8edmxQ-iq2_CnvAmeoYhtMufaLvKcAVp65sg1z6H71Ysr9sDMU-_Rpj5uwZr3OYTaoVKJrWb_j_d6r3q3yvNtEZTTbhEclFkToBktPmyNiAVM3IVeBfDQNIli4ykKIEad4NIuXRPSqo-6KOXSxMiDgfacJWgzmZ4DT2CE8V9u50eiabiygPIiaQIBW0uz3HWrrYNFDXMBxpJBcvX9WL_q3FD9gNbdTeyGiJyNcKDyDekxa8z-iH64jFBa4FlEoaB2q2_aDBgSpZmhwExl1B9SQYoQ48zwFV8u2UrrcxI5-1HZ-FvRx6R-yntZk6Ej6qwVM0sBZzxA\n")

        }
        binding?.signWithApple?.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_getAcquaintedFragment)
        }
    }

    private fun getPrivacySpan() = getString(
        R.string.privacy_policy_label,
        getString(R.string.privacy_policy)
    ).getClickableSpan(
        getString(R.string.privacy_policy),
        ContextCompat.getColor(requireContext(), R.color.blue),
        isHavingUnderline = false,
        shouldBeBold = true
    ) {
        Toast.makeText(requireContext(), getString(R.string.privacy_policy), Toast.LENGTH_SHORT)
            .show()
    }

    private fun getTermsSpan() = getString(
        R.string.terms_of_use_label,
        getString(R.string.terms_of_use)
    ).getClickableSpan(
        getString(R.string.terms_of_use),
        ContextCompat.getColor(requireContext(), R.color.blue),
        isHavingUnderline = false,
        shouldBeBold = true
    ) {
        Toast.makeText(requireContext(), getString(R.string.terms_of_use), Toast.LENGTH_SHORT)
            .show()
    }
}