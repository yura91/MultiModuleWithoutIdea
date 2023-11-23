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
import net.pst.cash.databinding.FragmentSelectBalanceBinding
import net.pst.cash.presentation.model.BalanceItemModel
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.CardIsReadyViewModel
import net.pst.cash.presentation.viewmodels.SharedViewModel

@AndroidEntryPoint
class SelectBalanceFragment :
    BaseFragment<FragmentSelectBalanceBinding>(FragmentSelectBalanceBinding::inflate) {
    private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.design_nav_graph)
    private val cardIsReadyViewModel: CardIsReadyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val balanceList = listOf(
            BalanceItemModel("500.00 $", "550.00 USDT"),
            BalanceItemModel("250.00 $", "280.00 USDT"),
            BalanceItemModel("100.00 $", "115.00 USDT"),
            BalanceItemModel("50.00 $", "60.00 USDT")
        )
        val balanceListAdapter = BalanceListAdapter(balanceList)
        binding?.balanceList?.adapter = balanceListAdapter

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
                    val negativeMargin = (height * 0.5).toInt()

                    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.topMargin = -negativeMargin
                    setLayoutParams(layoutParams)

                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        binding?.toolbar?.actionMore?.setOnClickListener {
            it.findNavController().navigate(R.id.action_cardIsReadyFragment_to_settings_nav_graph)
        }
        binding?.next?.setOnClickListener {
            val action =
                SelectBalanceFragmentDirections.actionCardIsReadyFragmentToCardInfoFragment(
                    cardIsReadyViewModel.cardId,
                    cardIsReadyViewModel.balance,
                    cardIsReadyViewModel.currencyType
                )
            findNavController().navigate(action)
        }
    }
}