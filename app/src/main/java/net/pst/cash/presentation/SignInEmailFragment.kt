package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentSignInEmailBinding
import net.pst.cash.presentation.viewmodels.SignInEmailViewModel


@AndroidEntryPoint
class SignInEmailFragment :
    BaseFragment<FragmentSignInEmailBinding>(FragmentSignInEmailBinding::inflate) {
    private val signInEmailViewModel: SignInEmailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.signInButton?.setOnClickListener {
            val email = binding?.emailField?.text.toString()
            val password = binding?.passwordField?.text.toString()
            if (email.isEmpty()) {
                binding?.emailField?.error = getString(R.string.field_email_can_not_be_empty)
            }
            if (password.isEmpty()) {
                binding?.passwordField?.error = getString(R.string.field_password_can_not_be_empty)
            }

            signInEmailViewModel.signInEmail(email, password)
        }
    }
}