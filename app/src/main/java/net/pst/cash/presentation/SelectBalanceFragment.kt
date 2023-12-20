package net.pst.cash.presentation

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentSelectBalanceBinding
import net.pst.cash.presentation.model.BalanceItemModel
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.SelectBalanceViewModel

@AndroidEntryPoint
class SelectBalanceFragment :
    BaseFragment<FragmentSelectBalanceBinding>(FragmentSelectBalanceBinding::inflate) {
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

        selectBalanceViewModel.mediatorLiveData.observe(viewLifecycleOwner) {
            val balanceItemModels: List<BalanceItemModel>? = it?.balanceItemModels
            val balance = it?.account
            binding?.toolbar?.cardBalance?.text = getString(R.string.usd, balance)
            if (!balanceItemModels.isNullOrEmpty() && balance != null) {
                val balanceListAdapter =
                    BalanceListAdapter(
                        balance,
                        balanceItemModels,
                        { remainedFunds, cardBalanceAmount ->
                            binding?.next?.text = getString(R.string.issue_card)
                            binding?.next?.isVisible = true
                            selectBalanceViewModel.enouphMoney = true
                            selectBalanceViewModel.remainedFunds = remainedFunds.toString()
                            selectBalanceViewModel.cardBalanceAmount = cardBalanceAmount.toString()
                        },
                        {
                            binding?.next?.text = getString(R.string.top_up_card)
                            binding?.next?.isVisible = true
                            selectBalanceViewModel.enouphMoney = false
                        })
                binding?.balanceList?.adapter = balanceListAdapter
            }
        }

        setGradient()

        binding?.selectBalanceImage?.apply {
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

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

        binding?.toolbar?.actionMore?.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(argsTag, true)
            findNavController().navigate(
                R.id.action_selectBalanceFragment_to_settings_nav_graph,
                bundle
            )
        }
        binding?.next?.setOnClickListener {
            if (selectBalanceViewModel.enouphMoney) {
                val action =
                    SelectBalanceFragmentDirections.actionSelectBalanceFragmentToCardInfoFragment(
                        selectBalanceViewModel.cardId,
                        selectBalanceViewModel.cardBalanceAmount,
                        selectBalanceViewModel.currencyType,
                        selectBalanceViewModel.remainedFunds
                    )
                findNavController().navigate(action)
            } else {
                val bundle = Bundle()
                bundle.putString(balanceKey, selectBalanceViewModel.balance)
                findNavController().navigate(
                    R.id.action_selectBalanceFragment_to_topUpFragment,
                    bundle,
                    navOptions
                )
            }
        }
    }

    private fun setGradient() {
        val sharedPref =
            requireContext().getSharedPreferences(getString(R.string.myprefs), Context.MODE_PRIVATE)
        val userId = sharedPref.getString("userId", "")
        val startColor = sharedPref.getInt(userId + getString(R.string.startcolor), defColorValue)
        val endColor = sharedPref.getInt(userId + getString(R.string.endcolor), defColorValue)

        if (startColor != defColorValue && endColor != defColorValue) {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(startColor, endColor)
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
            binding?.selectBalanceImage?.background = layerDrawable
        }
    }

    companion object {
        const val defColorValue = -1
        private const val balanceKey = "balance"
        private const val argsTag = "showAdditionalItems"
    }
}