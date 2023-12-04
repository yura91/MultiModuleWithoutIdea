package net.pst.cash.presentation

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
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentIssueCardBinding
import net.pst.cash.presentation.model.GradientModel
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.IssueCardViewModel
import net.pst.cash.presentation.viewmodels.SharedViewModel

@AndroidEntryPoint
class IssueCardFragment :
    BaseFragment<FragmentIssueCardBinding>(FragmentIssueCardBinding::inflate) {
    val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.design_nav_graph)
    private val issueCardViewModel: IssueCardViewModel by viewModels()
    private val balanceTag = "balance"
    private val currencyTag = "currency"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        issueCardViewModel.getActiveBalance()

        sharedViewModel.gradientData.observe(viewLifecycleOwner) { gradientData ->
            if (gradientData != null) {
                setGradient(gradientData)
            }
        }

        issueCardViewModel.cardModel.observe(viewLifecycleOwner) {
            var amount = ""
            it?.balance?.let { cardBalance ->
                amount = cardBalance
            }
            var currencySign = ""
            it?.currencyType?.let { currencyType ->
                currencySign = currencyType
            }
            binding?.toolbar?.cardBalance?.text = "$amount USDT"
        }

        binding?.toolbar?.actionMore?.setOnClickListener {
            findNavController().navigate(R.id.action_issueCardFragment_to_settings_nav_graph)
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
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

    private fun setGradient(
        gradientData: GradientModel
    ) {
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(gradientData.startColor, gradientData.endColor)
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