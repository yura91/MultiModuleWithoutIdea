package net.pst.cash.presentation

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentCardIsReadyBinding
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.CardIsReadyViewModel
import net.pst.cash.presentation.viewmodels.SharedViewModel

@AndroidEntryPoint
class CardIsReadyFragment :
    BaseFragment<FragmentCardIsReadyBinding>(FragmentCardIsReadyBinding::inflate) {
    private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.design_nav_graph)
    private val cardIsReadyViewModel: CardIsReadyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.gradientData.observe(viewLifecycleOwner) { gradientData ->
            if (gradientData != null) {
                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(gradientData.startColor, gradientData.endColor)
                )
                val outValue = TypedValue()
                resources.getValue(R.dimen.corner_radius, outValue, true)
                val cornerRadius = outValue.float
                val radii = floatArrayOf(
                    0f,
                    0f,
                    0f,
                    0f,
                    requireContext().dpToPx(cornerRadius),
                    requireContext().dpToPx(cornerRadius),
                    requireContext().dpToPx(cornerRadius),
                    requireContext().dpToPx(cornerRadius)
                )
                gradientDrawable.cornerRadii = radii
                val layer1 = gradientDrawable
                val layer2 =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.card_background_bg)
                val layers = arrayOf(layer1, layer2)
                val layerDrawable = LayerDrawable(layers)
                binding?.cardIsReadyImage?.background = layerDrawable
            }
        }
        cardIsReadyViewModel.cardModel.observe(viewLifecycleOwner) {
            Log.d("TAG", it.toString())
            it?.id?.let { idCard ->
                cardIsReadyViewModel.cardId = idCard
            }

            it?.currencyType?.let { currencyType ->
                cardIsReadyViewModel.currencyType = currencyType
            }

            it?.balance?.let { cardBalance ->
                cardIsReadyViewModel.balance = cardBalance
            }
            binding?.next?.isEnabled = true
        }
        cardIsReadyViewModel.checkActiveCards()
        binding?.cardIsReadyImage?.apply {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val height = height
                    val negativeMargin = (height * 0.3).toInt()

                    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.topMargin = -negativeMargin
                    setLayoutParams(layoutParams)

                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        binding?.actionMore?.setOnClickListener {
            it.findNavController().navigate(R.id.action_cardIsReadyFragment_to_settings_nav_graph)
        }
        binding?.next?.setOnClickListener {
            val action =
                CardIsReadyFragmentDirections.actionCardIsReadyFragmentToCardInfoFragment(
                    cardIsReadyViewModel.cardId,
                    cardIsReadyViewModel.balance,
                    cardIsReadyViewModel.currencyType
                )
            findNavController().navigate(action)
        }
    }
}