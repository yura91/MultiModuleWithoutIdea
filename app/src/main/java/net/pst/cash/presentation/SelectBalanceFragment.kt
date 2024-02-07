package net.pst.cash.presentation

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentSelectBalanceBinding
import net.pst.cash.presentation.model.BalanceItemModel
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.SelectBalanceViewModel

@AndroidEntryPoint
class SelectBalanceFragment :
    BaseDialogFragment<FragmentSelectBalanceBinding>(FragmentSelectBalanceBinding::inflate) {
    private val selectBalanceViewModel: SelectBalanceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.balanceList)
            .addItemDecoration(VerticalSpaceItemDecoration(8f.dpToPx().toInt()))

        selectBalanceViewModel.account.observe(viewLifecycleOwner) {
            view.findViewById<SwipeRefreshLayout>(R.id.swipeContainer).isRefreshing = false
            val balance = it
            val cardBalance = view.findViewById<TextView>(R.id.cardBalance)
            cardBalance.text = getString(R.string.usd, balance)
        }

        selectBalanceViewModel.buttonTopUpEvent.observe(viewLifecycleOwner) {
            view.findViewById<MaterialButton>(R.id.topUpCardButton).isVisible = true
            view.findViewById<MaterialButton>(R.id.issueCardButton).isVisible = false
        }

        selectBalanceViewModel.buttonIssueCardEvent.observe(viewLifecycleOwner) {
            view.findViewById<MaterialButton>(R.id.topUpCardButton).isVisible = false
            view.findViewById<MaterialButton>(R.id.issueCardButton).isVisible = true
        }

        selectBalanceViewModel.configData.observe(viewLifecycleOwner) {
            val balanceItemModels: List<BalanceItemModel>? = it
            if (!balanceItemModels.isNullOrEmpty()) {
                selectBalanceViewModel.calculateBalance()
                val balanceListAdapter =
                    BalanceListAdapter(
                        balanceItemModels,
                        selectBalanceViewModel.selectedPos
                    ) { balanceCard, costCard, selectedPos ->
                        selectBalanceViewModel.selectedPos = selectedPos
                        selectBalanceViewModel.balanceCard = balanceCard
                        selectBalanceViewModel.costCard = costCard
                        selectBalanceViewModel.calculateBalance()
                    }
                view.findViewById<RecyclerView>(R.id.balanceList).adapter = balanceListAdapter
            }
        }

        selectBalanceViewModel.snackBarErrorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show();
        }

        selectBalanceViewModel.issueCardEvent.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putInt("newCardId", it)
            findNavController().navigate(
                R.id.action_selectBalanceFragment_to_cardListFragment,
                bundle
            )
        }

        binding?.swipeContainer?.setOnRefreshListener {
            selectBalanceViewModel.getActiveBalance()
        }

        setGradient()

        binding?.selectBalanceImage?.apply {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val height = height
                    val negativeMargin = -(height * 0.6).toInt()

                    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.topMargin = negativeMargin
                    setLayoutParams(layoutParams)

                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }


        binding?.toolbar?.actionMore?.setOnClickListener {
            findNavController().navigate(
                R.id.action_selectBalanceFragment_to_settings_nav_graph,
            )
        }
        binding?.issueCardButton?.setOnClickListener {
            selectBalanceViewModel.issueCard()
        }

        binding?.topUpCardButton?.setOnClickListener {
            findNavController().navigate(
                R.id.action_selectBalanceFragment_to_topUpFragment
            )
        }
    }

    private fun setGradient() {
        val sharedPref =
            requireContext().getSharedPreferences(getString(R.string.myprefs), Context.MODE_PRIVATE)
        val userId = sharedPref.getString(getString(R.string.userid), "")
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
    }
}