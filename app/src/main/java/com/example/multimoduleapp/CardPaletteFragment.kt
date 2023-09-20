package com.example.multimoduleapp

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.multimoduleapp.databinding.FragmentCardPaletteBinding
import com.example.multimoduleapp.model.GradientModel
import com.example.multimoduleapp.viewmodels.CardPaletteViewModel
import com.example.multimoduleapp.viewmodels.SharedViewModel

class CardPaletteFragment :
    BaseFragment<FragmentCardPaletteBinding>(FragmentCardPaletteBinding::inflate) {

    private val layerCornerRadius = 30.0F

    private val gradientOffset = 25.0F

    private val cardPalleteViewModel by viewModels<CardPaletteViewModel>()
    private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.design_nav_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors: IntArray = intArrayOf(
            getColor(requireContext(), R.color.red_gr_color),
            getColor(requireContext(), R.color.third_gr_color),
            getColor(requireContext(), R.color.fourth_gr_color),
            getColor(requireContext(), R.color.fifth_gr_color),
            getColor(requireContext(), R.color.red_gr_color)
        )

        binding?.colorSeekBar?.setColorSeeds(colors)

        val colorProgress = cardPalleteViewModel.colorProgress
        colorProgress?.let {
            binding?.colorSeekBar?.progress = colorProgress
            setGradient()
        }
        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_cardPaletteFragment_to_cardIsReadyFragment)
        }

        binding?.actionMore?.setOnClickListener {
            findNavController().navigate(R.id.action_cardPaletteFragment_to_settings_nav_graph)
        }

        binding?.colorSeekBar?.setOnColorChangeListener { progress, color ->
            cardPalleteViewModel.colorProgress = progress
            setGradient(progress, color)
        }
    }

    private fun pickColor(progress: Int): Int {
        binding?.colorSeekBar?.let { colorSeekBar ->
            if (progress < 0) return Int.MAX_VALUE
            if (progress > colorSeekBar.maxProgress) return Int.MAX_VALUE
            return if (progress >= colorSeekBar.colors.size) Int.MAX_VALUE else colorSeekBar.colors[progress]
        }
        return Int.MAX_VALUE
    }

    //TODO вынести в Utils
    private fun Context.dpToPx(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale
    }

    private fun setGradient(progress: Int, color: Int?) {
        val endColor = color
        val colorPosition = progress - requireContext().dpToPx(gradientOffset)
        val startColor = pickColor(colorPosition.toInt())
        val gradientDrawable = endColor?.let {
            val gradientData = GradientModel(startColor, it)
            sharedViewModel.setGradientData(gradientData)
            GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(startColor, it)
            )
        }

        gradientDrawable?.cornerRadius = requireContext().dpToPx(layerCornerRadius)

        val layer1 = gradientDrawable
        val layer2 =
            getDrawable(requireContext(), R.drawable.card_design_bg)
        val layers = arrayOf(layer1, layer2)
        val layerDrawable = LayerDrawable(layers)
        binding?.cardDesign?.background = layerDrawable
    }

    private fun setGradient() {
        val startColor = sharedViewModel.gradientData.value?.startColor
        val endColor = sharedViewModel.gradientData.value?.endColor
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(startColor!!, endColor!!)
        )
        gradientDrawable.cornerRadius = requireContext().dpToPx(layerCornerRadius)
        val layer1 = gradientDrawable
        val layer2 =
            getDrawable(requireContext(), R.drawable.card_design_bg)
        val layers = arrayOf(layer1, layer2)
        val layerDrawable = LayerDrawable(layers)
        binding?.cardDesign?.background = layerDrawable
    }
}