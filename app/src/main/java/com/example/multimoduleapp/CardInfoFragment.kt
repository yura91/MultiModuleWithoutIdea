package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.multimoduleapp.databinding.FragmentCardInfoBinding


class CardInfoFragment :
    BaseFragment<FragmentCardInfoBinding>(FragmentCardInfoBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val frontBinding = binding?.restrictedCardInfo
        val backBinding = binding?.fullCardInfo
        frontBinding?.toolbar?.inflateMenu(R.menu.main_menu)
        val menu = frontBinding?.toolbar?.menu
        val menuItem = menu?.findItem(R.id.action_settings)
        menuItem?.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_completeAuthFragment_to_settingsFragment)
            return@setOnMenuItemClickListener false
        }
        frontBinding?.topUpButton?.setOnClickListener {
            it.findNavController().navigate(R.id.action_completeAuthFragment_to_topUpFragment)
        }

        frontBinding?.payments?.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_completeAuthFragment_to_historyPaymentsFragment)
        }
        frontBinding?.cardInfo?.setOnClickListener {
            binding?.easyFlipView?.flipTheView()
        }
        backBinding?.back?.setOnClickListener {
            binding?.easyFlipView?.flipTheView()
        }
    }
}