package com.example.multimoduleapp

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.multimoduleapp.databinding.FragmentCardInfoBinding
import com.example.multimoduleapp.databinding.FullCardInfoBinding
import com.example.multimoduleapp.databinding.RestrictedCardInfoBinding
import com.example.multimoduleapp.model.GradientModel
import com.example.multimoduleapp.model.dpToPx
import com.example.multimoduleapp.viewmodels.SharedViewModel
import com.google.android.material.shape.CornerFamily


class CardInfoFragment :
    BaseFragment<FragmentCardInfoBinding>(FragmentCardInfoBinding::inflate) {
    val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.design_nav_graph)
    var grModel = GradientModel(0, 0)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val frontBinding = binding?.restrictedCardInfo
        val backBinding = binding?.fullCardInfo
        val navController = findNavController()
        sharedViewModel.gradientData.observe(viewLifecycleOwner) { gradientData ->
            if (gradientData != null) {
                setGradient(gradientData, frontBinding, backBinding)
            }
        }

        val frontShapedImage = frontBinding?.cardInfo
        frontShapedImage?.let { imageview ->
            imageview.shapeAppearanceModel = imageview
                .shapeAppearanceModel
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, requireContext().dpToPx(30.0F))
                .build()
        }

        val backShapedImage = backBinding?.cardInfo
        backShapedImage?.let { imageview ->
            imageview.shapeAppearanceModel = imageview
                .shapeAppearanceModel
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, requireContext().dpToPx(30.0F))
                .build()
        }

        val navOptions =
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_top)
                .setExitAnim(R.anim.slide_out_top)
                .setPopEnterAnim(R.anim.slide_in_bottom)
                .setPopExitAnim(R.anim.slide_out_bottom)
                .build()

        frontBinding?.topUpButton?.setOnClickListener {
            navController.navigate(R.id.topUpFragment, null, navOptions)
        }

        frontBinding?.actionMore?.setOnClickListener {
            findNavController().navigate(R.id.action_cardInfoFragment_to_settings_nav_graph)
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

        requireActivity().onBackPressedDispatcher.addCallback {
            if (binding?.easyFlipView?.isBackSide == true) {
                binding?.easyFlipView?.setFlipTypeFromBack()
                binding?.easyFlipView?.flipTheView()
            }
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
        val outValue = TypedValue()
        resources.getValue(R.dimen.corner_radius, outValue, true)
        val cornerRadius = outValue.float
        gradientDrawable.cornerRadius = requireContext().dpToPx(cornerRadius)
        val layer1 = gradientDrawable
        val layer2 =
            AppCompatResources.getDrawable(requireContext(), R.drawable.card_background_bg)
        val layers = arrayOf(layer1, layer2)
        val layerDrawable = LayerDrawable(layers)
        frontBinding?.cardInfo?.background = layerDrawable
        backBinding?.cardInfo?.background = layerDrawable
    }
}