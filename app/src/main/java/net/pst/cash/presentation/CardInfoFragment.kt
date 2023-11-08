package net.pst.cash.presentation

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.google.android.material.shape.CornerFamily
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentCardInfoBinding
import net.pst.cash.databinding.FullCardInfoBinding
import net.pst.cash.databinding.RestrictedCardInfoBinding
import net.pst.cash.presentation.model.GradientModel
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.CardInfoViewModel
import net.pst.cash.presentation.viewmodels.SharedViewModel

@AndroidEntryPoint
class CardInfoFragment :
    BaseFragment<FragmentCardInfoBinding>(FragmentCardInfoBinding::inflate) {
    val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.design_nav_graph)
    private val cardInfoViewModel: CardInfoViewModel by viewModels()
    private val args: CardInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val frontBinding = binding?.restrictedCardInfo
        val backBinding = binding?.fullCardInfo
        val navController = findNavController()
        sharedViewModel.gradientData.observe(viewLifecycleOwner) { gradientData ->
            if (gradientData != null) {
                setGradient(gradientData, frontBinding, backBinding)
            }
        }

        cardInfoViewModel.getCardInfo(args.cardId.toString())

        cardInfoViewModel.cardInfoData.observe(viewLifecycleOwner) {
            it.number?.let { number ->
                val lastFourDigits = number.substring(number.length - 4)
                frontBinding?.cardNumTemp?.text = lastFourDigits
                backBinding?.cardNumber?.text = number
            }

            backBinding?.cvv?.text = it.cvx2
            val expMonth = it.expMonth
            val expYear = it.expYear
            backBinding?.expDate?.text = "$expMonth/$expYear"
            frontBinding?.expDate?.text = "$expMonth/$expYear"
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

        frontBinding?.topUpButton?.setOnClickListener {
            navController.navigate(R.id.topUpFragment, null, navOptions)
        }

        frontBinding?.actionMore?.setOnClickListener {
            findNavController().navigate(R.id.action_cardInfoFragment_to_settings_nav_graph)
        }

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
        val balance = args.balance
        val currencyType = args.currencyType
        frontBinding?.sum?.text = getString(R.string.balance, balance, currencyType)

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
        gradientData: GradientModel,
        frontBinding: RestrictedCardInfoBinding?,
        backBinding: FullCardInfoBinding?
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
            AppCompatResources.getDrawable(requireContext(), R.drawable.card_background_bg)
        val layers = arrayOf(layer1, layer2)
        val layerDrawable = LayerDrawable(layers)
        frontBinding?.cardInfo?.background = layerDrawable
        backBinding?.cardInfo?.background = layerDrawable
    }
}