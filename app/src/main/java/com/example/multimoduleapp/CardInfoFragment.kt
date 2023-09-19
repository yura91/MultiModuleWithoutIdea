package com.example.multimoduleapp

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.multimoduleapp.databinding.FragmentCardInfoBinding
import com.example.multimoduleapp.databinding.FullCardInfoBinding
import com.example.multimoduleapp.databinding.RestrictedCardInfoBinding
import com.example.multimoduleapp.model.GradientModel
import com.example.multimoduleapp.viewmodels.SharedViewModel


class CardInfoFragment :
    BaseFragment<FragmentCardInfoBinding>(FragmentCardInfoBinding::inflate) {
    val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.nav_graph)
    var grModel = GradientModel(0, 0)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val frontBinding = binding?.restrictedCardInfo
        val backBinding = binding?.fullCardInfo
        frontBinding?.toolbar?.inflateMenu(R.menu.main_menu)
        val menu = frontBinding?.toolbar?.menu
        val menuItem = menu?.findItem(R.id.action_settings)
        val navController = findNavController()

        sharedViewModel.gradientData.observe(viewLifecycleOwner) { gradientData ->
            if (gradientData != null) {
                setGradient(gradientData, frontBinding, backBinding)
            }
        }

        binding?.easyFlipView?.setOnFlipListener { easyFlipView, newCurrentSide ->
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(grModel.startColor, grModel.endColor)
            )
            gradientDrawable.cornerRadius = requireContext().dpToPx(grModel.cornerRadius)
            val layer1 = gradientDrawable
            val layer2 =
                AppCompatResources.getDrawable(requireContext(), R.drawable.card_background_bg)
            val layers = arrayOf(layer1, layer2)
            val layerDrawable = LayerDrawable(layers)
            frontBinding?.cardInfo?.background = layerDrawable
        }

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

    private fun setGradient(
        gradientData: GradientModel,
        frontBinding: RestrictedCardInfoBinding?,
        backBinding: FullCardInfoBinding?
    ) {
        grModel = gradientData
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(gradientData.startColor, gradientData.endColor)
        )
        gradientDrawable.cornerRadius = requireContext().dpToPx(gradientData.cornerRadius)
        val layer1 = gradientDrawable
        val layer2 =
            AppCompatResources.getDrawable(requireContext(), R.drawable.card_background_bg)
        val layers = arrayOf(layer1, layer2)
        val layerDrawable = LayerDrawable(layers)
        frontBinding?.cardInfo?.background = layerDrawable
        backBinding?.cardInfo?.background = layerDrawable
    }

    fun Context.dpToPx(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale
    }
}