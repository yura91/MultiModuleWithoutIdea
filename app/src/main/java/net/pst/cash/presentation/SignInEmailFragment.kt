package net.pst.cash.presentation

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentSignInEmailBinding
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.SignInEmailViewModel


@AndroidEntryPoint
class SignInEmailFragment :
    BaseFragment<FragmentSignInEmailBinding>(FragmentSignInEmailBinding::inflate) {
    private val signInEmailViewModel: SignInEmailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contentView: View = requireActivity().findViewById(android.R.id.content)
        contentView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            contentView.getWindowVisibleDisplayFrame(r)
            val screenHeight = contentView.rootView.height

            // Высота, которую занимает клавиатура
            val keypadHeight = screenHeight - r.bottom
            if (keypadHeight > screenHeight * 0.15) { // Если клавиатура открыта
                // Поднимаем или изменяем ваш макет здесь
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(
                    requireContext().dpToPx(20f).toInt(),
                    requireContext().dpToPx(20f).toInt(),
                    requireContext().dpToPx(20f).toInt(),
                    requireContext().dpToPx(150f).toInt()
                )
                binding?.signInButton?.layoutParams = params
            } else {
                // Возвращаем макет в исходное положение, если клавиатура закрыта
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(
                    requireContext().dpToPx(20f).toInt(),
                    requireContext().dpToPx(20f).toInt(),
                    requireContext().dpToPx(20f).toInt(),
                    requireContext().dpToPx(0f).toInt()
                )
                binding?.signInButton?.layoutParams = params
            }
        }


        signInEmailViewModel.snackBarErrorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show();
        }

        signInEmailViewModel.emptyEmailLiveData.observe(viewLifecycleOwner) {
            binding?.emailField?.error = getString(R.string.field_email_can_not_be_empty)
        }

        signInEmailViewModel.emptyPasswordLiveData.observe(viewLifecycleOwner) {
            binding?.passwordField?.error = getString(R.string.field_password_can_not_be_empty)
        }
        signInEmailViewModel.sendSignInReqLiveData.observe(viewLifecycleOwner) {
            signInEmailViewModel.signInEmail(it.email, it.password)
        }
        binding?.signInButton?.setOnClickListener {
            val email = binding?.emailField?.text.toString()
            val password = binding?.passwordField?.text.toString()
            signInEmailViewModel.validateForm(email, password)
        }
    }
}