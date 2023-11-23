package net.pst.cash.presentation

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import net.pst.cash.R
import net.pst.cash.databinding.FragmentIssueCardBinding
import net.pst.cash.presentation.model.GradientModel
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.SharedViewModel

class IssueCardFragment :
    BaseFragment<FragmentIssueCardBinding>(FragmentIssueCardBinding::inflate) {
    val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.design_nav_graph)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.gradientData.observe(viewLifecycleOwner) { gradientData ->
            if (gradientData != null) {
                setGradient(gradientData)
            }
        }

        binding?.toolbar?.actionMore?.setOnClickListener {
            findNavController().navigate(R.id.action_issueCardFragment_to_settings_nav_graph)
        }

        binding?.next?.setOnClickListener {
            findNavController().navigate(R.id.action_issueCardFragment_to_cardIsReadyFragment)
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