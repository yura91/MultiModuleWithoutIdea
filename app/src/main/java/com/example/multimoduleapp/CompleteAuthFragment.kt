package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.multimoduleapp.databinding.FragmentCompleteAuthBinding


class CompleteAuthFragment :
    BaseFragment<FragmentCompleteAuthBinding>(FragmentCompleteAuthBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbar?.inflateMenu(R.menu.main_menu)
        val menu = binding?.toolbar?.menu
        val menuItem = menu?.findItem(R.id.action_settings)
        menuItem?.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_completeAuthFragment_to_settingsFragment)
            return@setOnMenuItemClickListener false
        }
        binding?.topUpButton?.setOnClickListener {
            it.findNavController().navigate(R.id.action_completeAuthFragment_to_topUpFragment)
        }

        binding?.payments?.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_completeAuthFragment_to_historyPaymentsFragment)
        }

        /*binding?.cardInfo?.setOnClickListener {
           it.findNavController().navigate(R.id.action_completeAuthFragment_to_fullCardInfoFragment)
        }*/
    }
}