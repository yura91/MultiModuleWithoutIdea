package com.example.multimoduleapp

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.example.multimoduleapp.databinding.FragmentCardPaletteBinding

class CardPaletteFragment : Fragment() {
    private lateinit var binding: FragmentCardPaletteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardPaletteBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val layerCornerRadius = 80.0F

    private val gradientOffset = 55

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

        binding.colorSeekBar.setColorSeeds(colors)

        binding.colorSeekBar.setOnColorChangeListener { progress, color ->
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
            binding.cardDesign.background = layerDrawable
        }
    }

    private fun pickColor(progress: Int): Int {
        if (progress < 0) return Int.MAX_VALUE
        if (progress > binding.colorSeekBar.maxProgress) return Int.MAX_VALUE
        return if (progress >= binding.colorSeekBar.colors.size) Int.MAX_VALUE else binding.colorSeekBar.colors[progress]
    }
}