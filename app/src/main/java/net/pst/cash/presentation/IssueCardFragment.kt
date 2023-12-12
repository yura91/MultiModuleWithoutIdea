package net.pst.cash.presentation

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentIssueCardBinding
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.IssueCardViewModel

@AndroidEntryPoint
class IssueCardFragment :
    BaseFragment<FragmentIssueCardBinding>(FragmentIssueCardBinding::inflate) {
    private val issueCardViewModel: IssueCardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        issueCardViewModel.getActiveBalance()

        setGradient()

        issueCardViewModel.cardModel.observe(viewLifecycleOwner) {
            var amount = ""
            it?.balance?.let { cardBalance ->
                amount = cardBalance
            }
            var currencySign = ""
            it?.currencyType?.let { currencyType ->
                currencySign = currencyType
            }
            binding?.toolbar?.cardBalance?.text = getString(R.string.usdt, amount)
        }

        binding?.toolbar?.actionMore?.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(argsTag, true)
            findNavController().navigate(
                R.id.action_issueCardFragment_to_settings_nav_graph,
                bundle
            )
        }
        requireActivity().onBackPressedDispatcher.addCallback {
                requireActivity().finish();
        }
        binding?.next?.setOnClickListener {
            val navOptions =
                NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_top)
                    .setExitAnim(R.anim.slide_out_top)
                    .setPopEnterAnim(R.anim.slide_in_bottom)
                    .setPopExitAnim(R.anim.slide_out_bottom)
                    .build()
            val bundle = Bundle()
            bundle.putString(balanceTag, issueCardViewModel.getCardBalance())
            bundle.putString(currencyTag, issueCardViewModel.getCurrency())
            findNavController().navigate(
                R.id.action_issueCardFragment_to_selectBalanceFragment,
                bundle,
                navOptions
            )
        }
    }

    private fun setGradient() {
        val sharedPref =
            requireContext().getSharedPreferences(getString(R.string.myprefs), Context.MODE_PRIVATE)
        val startColor = sharedPref.getInt(getString(R.string.startcolor), defColorValue)
        val endColor = sharedPref.getInt(getString(R.string.endcolor), defColorValue)

        if (startColor != defColorValue && endColor != defColorValue) {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(startColor, endColor)
            )
            val outValue = TypedValue()
            resources.getValue(R.dimen.corner_radius, outValue, true)
            val cornerRadius = outValue.float
            gradientDrawable.cornerRadius = requireContext().dpToPx(cornerRadius)
            val layer1 = gradientDrawable
            val layer2 =
                AppCompatResources.getDrawable(requireContext(), R.drawable.issue_card)
            val layers = arrayOf(layer1, layer2)
            val layerDrawable = LayerDrawable(layers)
            binding?.cardImage?.background = layerDrawable
        }
    }

    companion object {
        const val defColorValue = -1
        private const val balanceTag = "balance"
        private const val currencyTag = "currency"
        private const val argsTag = "showAdditionalItems"
    }
}