package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavOptions
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
        val navController = findNavController()

        val navOptions =
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_top)
                .setExitAnim(R.anim.slide_out_top)
                .setPopEnterAnim(R.anim.slide_in_bottom)
                .setPopExitAnim(R.anim.slide_out_bottom)
                .build()

        menuItem?.setOnMenuItemClickListener {
            navController.navigate(R.id.action_completeAuthFragment_to_settingsFragment)
            return@setOnMenuItemClickListener false
        }
        frontBinding?.topUpButton?.setOnClickListener {
            navController.navigate(R.id.topUpFragment, null, navOptions)
        }

        frontBinding?.payments?.setOnClickListener {
            navController.navigate(R.id.historyPaymentsFragment, null, navOptions)
        }
        frontBinding?.cardInfo?.setOnClickListener {
            binding?.easyFlipView?.setFlipTypeFromFront()
            binding?.easyFlipView?.flipTheView()
        }
        backBinding?.back?.setOnClickListener {
            binding?.easyFlipView?.setFlipTypeFromBack()
            binding?.easyFlipView?.flipTheView()
        }

        backBinding?.copyCardNumber?.setOnClickListener {
            Toast.makeText(requireContext(), "Card number is copied", Toast.LENGTH_SHORT).show()
        }

        backBinding?.copyCvv?.setOnClickListener {
            Toast.makeText(requireContext(), "CVV is copied", Toast.LENGTH_SHORT).show()
        }

        backBinding?.copyCardHolderName?.setOnClickListener {
            Toast.makeText(requireContext(), "CardHolder name is copied", Toast.LENGTH_SHORT).show()
        }

        backBinding?.copyExpDate?.setOnClickListener {
            Toast.makeText(requireContext(), "Exp date is copied", Toast.LENGTH_SHORT).show()
        }
    }
}