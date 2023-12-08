package net.pst.cash.presentation

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import net.pst.cash.R
import net.pst.cash.databinding.FragmentCardPaletteBinding
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.CardPaletteViewModel


class CardPaletteFragment :
    BaseFragment<FragmentCardPaletteBinding>(FragmentCardPaletteBinding::inflate) {
    private val gradientOffset = 25.0F
    private val initialProgress = 0
    private val initialColor = -312545
    private val argsKey = "clearBackStack"
    private val cardPalleteViewModel by viewModels<CardPaletteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val clearBackStack = args?.getBoolean(argsKey)

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
            it.findNavController().navigate(R.id.action_cardPaletteFragment_to_issueCardFragment)
        }

        binding?.actionMore?.setOnClickListener {
            findNavController().navigate(R.id.action_cardPaletteFragment_to_settings_nav_graph)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (clearBackStack != null && clearBackStack == true) {
                requireActivity().finish()
            } else {
                findNavController().popBackStack()
            }
        }

        binding?.toolbar?.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding?.colorSeekBar?.setOnColorChangeListener { progress, color ->
            cardPalleteViewModel.colorProgress = progress
            setGradient(progress, color)
        }
        if (cardPalleteViewModel.colorProgress == null) {
            setGradient(initialProgress, initialColor)
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

    private fun setGradient(progress: Int, color: Int?) {
        val endColor = color
        val colorPosition = progress - requireContext().dpToPx(gradientOffset)
        val startColor = pickColor(colorPosition.toInt())
        val gradientDrawable = endColor?.let {
            val sharedPref = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putInt("startColor", startColor)
                putInt("endColor", it)
                apply()
            }
            GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(startColor, it)
            )
        }

        val outValue = TypedValue()
        resources.getValue(R.dimen.corner_radius, outValue, true)
        val radius = outValue.float

        gradientDrawable?.cornerRadius = requireContext().dpToPx(radius)

        val layer1 = gradientDrawable
        val layer2 =
            getDrawable(requireContext(), R.drawable.card_design_bg)
        val layers = arrayOf(layer1, layer2)
        val layerDrawable = LayerDrawable(layers)
        binding?.cardDesign?.background = layerDrawable
    }

    private fun setGradient() {
        val sharedPref = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val startColor = sharedPref.getInt("startColor", -1)
        val endColor = sharedPref.getInt("endColor", -1)
        if (startColor != -1 && endColor != -1) {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(startColor, endColor)
            )
            val outValue = TypedValue()
            resources.getValue(R.dimen.corner_radius, outValue, true)
            val radius = outValue.float
            gradientDrawable.cornerRadius = requireContext().dpToPx(radius)
            val layer1 = gradientDrawable
            val layer2 =
                getDrawable(requireContext(), R.drawable.card_design_bg)
            val layers = arrayOf(layer1, layer2)
            val layerDrawable = LayerDrawable(layers)
            binding?.cardDesign?.background = layerDrawable
        }
    }
}