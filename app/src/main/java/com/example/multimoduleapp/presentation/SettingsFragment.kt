package com.example.multimoduleapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.multimoduleapp.R
import com.example.multimoduleapp.databinding.FragmentSettingsBinding


class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.helpSupport?.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingsFragment_to_helpSupportFragment)
        }

        binding?.legalInformation?.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_settingsFragment_to_legalInformationFragment)
        }

        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        binding?.logOut?.setOnClickListener {
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(
                requireContext()
            );

            alertDialogBuilder.setTitle(
                getString(R.string.log_out_dialog_title)
            );

            alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(
                    getString(R.string.log_out_positive)
                ) { dialog, which ->
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.you_are_logged_out),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton(
                    getString(R.string.log_out_negative)
                ) { dialog, which ->
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.you_are_not_logged_out),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            val alertDialog: AlertDialog = alertDialogBuilder.create();
            alertDialog.show()
        }
    }
}