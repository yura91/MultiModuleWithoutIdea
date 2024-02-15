package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import net.pst.cash.databinding.FragmentSignInEmailBinding


class SignInEmailFragment :
    BaseFragment<FragmentSignInEmailBinding>(FragmentSignInEmailBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.signInButton?.setOnClickListener {
            val email = binding?.emailField?.text.toString()
            val password = binding?.passwordField?.text.toString()
            if (email.isEmpty()) {
                binding?.emailField?.error = "Field email can not be empty"
            }
            if (password.isEmpty()) {
                binding?.passwordField?.error = "Field password can not be empty"
            }
        }
    }
}