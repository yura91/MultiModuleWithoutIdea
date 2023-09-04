package com.example.multimoduleapp

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentCardPaletteBinding

class CardPaletteFragment :
    BaseFragment<FragmentCardPaletteBinding>(FragmentCardPaletteBinding::inflate) {

    private val layerCornerRadius = 80.0F

    private val gradientOffset = 10

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors: IntArray = intArrayOf(
            getColor(requireContext(), R.color.first_gr_color),
            getColor(requireContext(), R.color.second_gr_color),
            getColor(requireContext(), R.color.third_gr_color),
            getColor(requireContext(), R.color.fourth_gr_color),
            getColor(requireContext(), R.color.fifth_gr_color),
            getColor(requireContext(), R.color.sixth_gr_color)
        )

        binding?.colorSeekBar?.setColorSeeds(colors)

        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_cardPaletteFragment_to_cardIsReadyFragment)
        }

        binding?.colorSeekBar?.setOnColorChangeListener { progress, color ->
            val startColor = color
            val colorPosition = progress - gradientOffset
            val endColor = pickColor(colorPosition)

            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(startColor, endColor)
            )

            gradientDrawable.cornerRadius = layerCornerRadius


            val layer1 = gradientDrawable
            val layer2 =
                getDrawable(requireContext(), R.drawable.card_design)
            val layers = arrayOf(layer1, layer2)
            val layerDrawable = LayerDrawable(layers)
            binding?.cardDesign?.background = layerDrawable
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
}