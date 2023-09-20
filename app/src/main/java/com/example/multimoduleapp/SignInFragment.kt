package com.example.multimoduleapp

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentSignInBinding
import com.example.multimoduleapp.model.getClickableSpan


class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val agreementSpan = SpannableStringBuilder()
        agreementSpan.append(getPrivacySpan())
        agreementSpan.append(getTermsSpan())
        binding?.textPrivacyPolicy?.text = agreementSpan
        binding?.textPrivacyPolicy?.movementMethod = LinkMovementMethod.getInstance()
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