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
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentSelectBalanceBinding
import net.pst.cash.presentation.model.BalanceItemModel
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.SelectBalanceViewModel
import net.pst.cash.presentation.viewmodels.SharedViewModel

@AndroidEntryPoint
class SelectBalanceFragment :
    BaseFragment<FragmentSelectBalanceBinding>(FragmentSelectBalanceBinding::inflate) {
    private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.design_nav_graph)
    private val selectBalanceViewModel: SelectBalanceViewModel by viewModels()
    private val navOptions =
        NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_top)
            .setExitAnim(R.anim.slide_out_top)
            .setPopEnterAnim(R.anim.slide_in_bottom)
            .setPopExitAnim(R.anim.slide_out_bottom)
            .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val balanceList = listOf(
            BalanceItemModel("500.00 $", "550.00 USDT"),
            BalanceItemModel("250.00 $", "280.00 USDT"),
            BalanceItemModel("100.00 $", "115.00 USDT"),
            BalanceItemModel("50.00 $", "60.00 USDT")
        )
        val balanceListAdapter = BalanceListAdapter(balanceList, {
            binding?.next?.text = getString(R.string.issue_card)
            binding?.next?.isVisible = true
            selectBalanceViewModel.enouphMoney = true
        }, {
            binding?.next?.text = getString(R.string.top_up_card)
            binding?.next?.isVisible = true
            selectBalanceViewModel.enouphMoney = false
        })
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
        selectBalanceViewModel.cardModel.observe(viewLifecycleOwner) {
            Log.d("TAG", it.toString())
            it?.id?.let { idCard ->
                selectBalanceViewModel.cardId = idCard
            }

            it?.currencyType?.let { currencyType ->
                selectBalanceViewModel.currencyType = currencyType
            }

            it?.balance?.let { cardBalance ->
                selectBalanceViewModel.balance = cardBalance
            }
            binding?.next?.isEnabled = true
        }
        selectBalanceViewModel.checkActiveCards()
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
            if (selectBalanceViewModel.enouphMoney) {
                val action =
                    SelectBalanceFragmentDirections.actionSelectBalanceFragmentToCardInfoFragment(
                        selectBalanceViewModel.cardId,
                        selectBalanceViewModel.balance,
                        selectBalanceViewModel.currencyType
                    )
                findNavController().navigate(action)
            } else {
                findNavController().navigate(
                    R.id.action_selectBalanceFragment_to_topUpFragment,
                    null,
                    navOptions
                )
            }
        }
    }
}