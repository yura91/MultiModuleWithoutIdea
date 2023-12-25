package net.pst.cash.presentation

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.google.android.material.shape.CornerFamily
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.pst.cash.R
import net.pst.cash.databinding.FragmentCardInfoBinding
import net.pst.cash.databinding.FullCardInfoBinding
import net.pst.cash.databinding.RestrictedCardInfoBinding
import net.pst.cash.presentation.model.HistoryItem
import net.pst.cash.presentation.model.RowHistoryItems
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.CardInfoViewModel

@AndroidEntryPoint
class CardInfoFragment :
    BaseFragment<FragmentCardInfoBinding>(FragmentCardInfoBinding::inflate) {
    private val cardInfoViewModel: CardInfoViewModel by viewModels()
    private val historyAdapter = HistoryPaymentsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val frontBinding = binding?.restrictedCardInfo
        val backBinding = binding?.fullCardInfo
        val toolbarBinding = binding?.toolbar

        val navController = findNavController()
        val historyItems = mutableListOf<HistoryItem>()
        val historyItem1 = HistoryItem("100", "Netfix", "23:55")
        val historyItem2 = HistoryItem("200", "Netfix", "23:55")
        val historyItem3 = HistoryItem("300", "Netfix", "00:56")
        val historyItem4 = HistoryItem("400", "Netfix", "00:55")

        historyItems.add(historyItem1)
        historyItems.add(historyItem2)
        historyItems.add(historyItem3)
        historyItems.add(historyItem4)

        val rowHistoryItems = mutableListOf<RowHistoryItems>()
        val rowHistoryItem = RowHistoryItems("23.11.04", historyItems)
        rowHistoryItems.add(rowHistoryItem)

        frontBinding?.simplePaymentList?.adapter = historyAdapter

        lifecycleScope.launch {
            historyAdapter.submitData(PagingData.from(rowHistoryItems))
        }

        setGradient(frontBinding, backBinding)

        frontBinding?.swipeContainer?.setOnRefreshListener {
            cardInfoViewModel.getActiveBalance()
        }

        toolbarBinding?.actionMore?.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(argsTag, true)
            findNavController().navigate(
                R.id.action_cardInfoFragment_to_settings_nav_graph,
                bundle
            )
        }

        cardInfoViewModel.cardModel.observe(viewLifecycleOwner) {
            var amount = ""
            it?.balance?.let { cardBalance ->
                amount = cardBalance
            }
            var currencySign = ""
            it?.currencyType?.let { currencyType ->
                currencySign = currencyType
            }
            frontBinding?.swipeContainer?.isRefreshing = false
            frontBinding?.cardBalance?.text = getString(R.string.balance, amount, currencySign)
        }

        val frontShapedImage = frontBinding?.cardInfo
        frontShapedImage?.let { imageview ->
            imageview.shapeAppearanceModel = imageview
                .shapeAppearanceModel
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, requireContext().dpToPx(30.0F))
                .build()
        }

        val backShapedImage = backBinding?.cardInfo
        backShapedImage?.let { imageview ->
            imageview.shapeAppearanceModel = imageview
                .shapeAppearanceModel
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, requireContext().dpToPx(30.0F))
                .build()
        }

        val navOptions =
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_top)
                .setExitAnim(R.anim.slide_out_top)
                .setPopEnterAnim(R.anim.slide_in_bottom)
                .setPopExitAnim(R.anim.slide_out_bottom)
                .build()

        frontBinding?.payments?.setOnClickListener {
            navController.navigate(R.id.historyPaymentsFragment, null, navOptions)
        }

        backBinding?.back?.setOnClickListener {
            binding?.easyFlipView?.setFlipTypeFromBack()
            binding?.easyFlipView?.flipTheView()
        }

        frontBinding?.rotate?.setOnClickListener {
            binding?.easyFlipView?.setFlipTypeFromFront()
            binding?.easyFlipView?.flipTheView()
        }

        backBinding?.copyCardNumber?.setOnClickListener {
            Toast.makeText(requireContext(), "Card number is copied", Toast.LENGTH_SHORT).show()
        }

        backBinding?.copyCvv?.setOnClickListener {
            Toast.makeText(requireContext(), "CVV is copied", Toast.LENGTH_SHORT).show()
        }

        backBinding?.copyExpDate?.setOnClickListener {
            Toast.makeText(requireContext(), "Exp date is copied", Toast.LENGTH_SHORT).show()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding?.easyFlipView?.isBackSide == true) {
                binding?.easyFlipView?.setFlipTypeFromBack()
                binding?.easyFlipView?.flipTheView()
            }
        }
    }

    private fun setGradient(
        frontBinding: RestrictedCardInfoBinding?,
        backBinding: FullCardInfoBinding?
    ) {
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
            gradientDrawable.cornerRadius = requireContext().dpToPx(cornerRadius)
            val layer1 = gradientDrawable
            val layer2 =
                AppCompatResources.getDrawable(requireContext(), R.drawable.card_background_bg)
            val layers = arrayOf(layer1, layer2)
            val layerDrawable = LayerDrawable(layers)
            frontBinding?.cardInfo?.background = layerDrawable
            backBinding?.cardInfo?.background = layerDrawable
        }
    }

    companion object {
        const val defColorValue = -1
        private const val argsTag = "showAdditionalItems"
    }
}