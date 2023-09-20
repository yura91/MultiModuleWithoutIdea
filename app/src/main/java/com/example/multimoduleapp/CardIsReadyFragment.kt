package com.example.multimoduleapp

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.multimoduleapp.databinding.FragmentCardIsReadyBinding
import com.example.multimoduleapp.viewmodels.SharedViewModel

class CardIsReadyFragment :
    BaseFragment<FragmentCardIsReadyBinding>(FragmentCardIsReadyBinding::inflate) {
    private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.design_nav_graph)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.gradientData.observe(viewLifecycleOwner) { gradientData ->
            if (gradientData != null) {
                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(gradientData.startColor, gradientData.endColor)
                )
                val radii = floatArrayOf(
                    0f,
                    0f,
                    0f,
                    0f,
                    requireContext().dpToPx(gradientData.cornerRadius),
                    requireContext().dpToPx(gradientData.cornerRadius),
                    requireContext().dpToPx(gradientData.cornerRadius),
                    requireContext().dpToPx(gradientData.cornerRadius)
                )
                gradientDrawable.cornerRadii = radii
                val layer1 = gradientDrawable
                val layer2 =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.card_is_ready_bg)
                val layers = arrayOf(layer1, layer2)
                val layerDrawable = LayerDrawable(layers)
                binding?.cardIsReadyImage?.background = layerDrawable
            }
        }
        binding?.actionMore?.setOnClickListener {
            it.findNavController().navigate(R.id.action_cardIsReadyFragment_to_settings_nav_graph)
        }
        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_cardIsReadyFragment_to_cardInfoFragment)
        }
    }

    //TODO вынести в Utils
    fun Context.dpToPx(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale
    }
}