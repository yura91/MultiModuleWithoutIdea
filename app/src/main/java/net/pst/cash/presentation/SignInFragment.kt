package net.pst.cash.presentation

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
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentSignInBinding
import net.pst.cash.presentation.model.getClickableSpan
import net.pst.cash.presentation.viewmodels.SignInViewModel

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private val signInViewModel: SignInViewModel by viewModels()
    private val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            signInViewModel.handleSignInResult(result)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val agreementSpan = SpannableStringBuilder()
        agreementSpan.append(getPrivacySpan())
        agreementSpan.append(getTermsSpan())
        binding?.textPrivacyPolicy?.text = agreementSpan
        binding?.textPrivacyPolicy?.movementMethod = LinkMovementMethod.getInstance()

        signInViewModel.signInRequest.observe(viewLifecycleOwner) { request ->
            activityResultLauncher.launch(request)
        }

        signInViewModel.isGoogleSuccess.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_signInFragment_to_getAcquaintedFragment)
        }

        signInViewModel.appleLink.observe(viewLifecycleOwner) { link ->
            link?.let {
                val action = SignInFragmentDirections.actionSignInFragmentToWebViewFragment(it)
                findNavController().navigate(action)
                signInViewModel.clearAppleLink()
            }
        }
        binding?.signWithGoogle?.setOnClickListener {
            signInViewModel.startSignIn()
        }
        binding?.signWithApple?.setOnClickListener {
            signInViewModel.getAppleLink()
//            it.findNavController().navigate(R.id.action_signInFragment_to_getAcquaintedFragment)
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