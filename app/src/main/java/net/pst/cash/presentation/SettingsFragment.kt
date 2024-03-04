package net.pst.cash.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentSettingsBinding
import net.pst.cash.presentation.viewmodels.CardListViewModel


@AndroidEntryPoint
class SettingsFragment :
    BaseDialogFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private val argsKey = "showAdditionalItems"
    private val cardIdKey = "cardId"
    private val accountIdKey = "accountId"
    private val cardListViewModel: CardListViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val args = arguments
        val showItems = args?.getBoolean(argsKey)
        val cardId = args?.getInt(cardIdKey)
        val accountId = args?.getInt(accountIdKey)

        if (showItems != null && showItems == true) {
            binding?.redesign?.isVisible = true
            binding?.closeCard?.isVisible = true
        } else {
            binding?.redesign?.isVisible = false
            binding?.closeCard?.isVisible = false
        }
        binding?.helpSupport?.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_helpSupportFragment)
        }

        binding?.legalInformation?.setOnClickListener {
            findNavController()
                .navigate(R.id.action_settingsFragment_to_legalInformationFragment)
        }

        binding?.toolbar?.setNavigationOnClickListener {
            dismiss()
        }

        binding?.closeCard?.setOnClickListener {
            showDialog(
                R.string.close_card_dialog_title,
                R.string.close_card_positive,
                R.string.close_card_negative,
                {
                    cardListViewModel.deleteCard(cardId, accountId)
                    dismiss()
                },
                {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.card_is_not_closed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        binding?.redesign?.setOnClickListener {
            findNavController().navigate(
                R.id.action_settingsFragment_to_cardPaletteDialogFragment,
            )
        }

        binding?.logOut?.setOnClickListener {
            val sharedPref = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.remove("token")
            editor.apply();
            showDialog(
                R.string.log_out_dialog_title, R.string.log_out_positive, R.string.log_out_negative,
                {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.you_are_logged_out),
                        Toast.LENGTH_SHORT
                    ).show()
                    cardListViewModel.cancelAllJobs()
                    findNavController().navigate(R.id.action_settingsFragment_to_signInFragment)
                },
                {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.you_are_not_logged_out),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}