package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
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

        binding?.logOut?.setOnClickListener {
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(
                requireContext()
            );

            // set title
            alertDialogBuilder.setTitle(
                "Are you sure you want \n" +
                        "to log out?"
            );

            // set dialog message
            alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(
                    "Log out"
                ) { dialog, which ->
                    Toast.makeText(
                        requireContext(),
                        "You are logged out",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton(
                    "No"
                ) { dialog, which ->
                    Toast.makeText(
                        requireContext(),
                        "You are not logged out",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            val alertDialog: AlertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show()
        }
    }
}