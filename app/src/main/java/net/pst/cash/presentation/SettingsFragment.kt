package net.pst.cash.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import net.pst.cash.R
import net.pst.cash.databinding.FragmentSettingsBinding


class SettingsFragment :
    BaseDialogFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private val argsKey = "showAdditionalItems"
    private val argsTag = "clearBackStack"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val args = arguments
        val showItems = args?.getBoolean(argsKey)

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
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        binding?.closeCard?.setOnClickListener {
            showDialog(
                R.string.close_card_dialog_title,
                R.string.close_card_positive,
                R.string.close_card_negative,
                {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.card_is_closed),
                        Toast.LENGTH_SHORT
                    ).show()
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
            val bundle = Bundle()
            bundle.putBoolean(argsTag, true)
            findNavController().navigate(
                R.id.action_settingsFragment_to_cardPaletteFragment,
                bundle
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
                    requireActivity().finish()
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