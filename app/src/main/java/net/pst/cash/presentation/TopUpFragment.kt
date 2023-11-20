package net.pst.cash.presentation

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentTopUpBinding
import net.pst.cash.presentation.model.dpToPx
import net.pst.cash.presentation.viewmodels.AccountsViewModel
import net.pst.cash.presentation.viewmodels.SharedViewModel

@AndroidEntryPoint
class TopUpFragment : BaseFragment<FragmentTopUpBinding>(FragmentTopUpBinding::inflate) {
    private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.design_nav_graph)
    private val accountsViewModel: AccountsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountsViewModel.addresses.observe(viewLifecycleOwner) {
            val address = it[0]
            binding?.copyQr?.text = address
            binding?.copyQr?.isVisible = true
            binding?.share?.isVisible = true
            accountsViewModel.generateQrCodeInBackground(address)
        }

        accountsViewModel.qrCodeLiveData.observe(viewLifecycleOwner) {
            binding?.qrCode?.setImageBitmap(it)
        }

        sharedViewModel.gradientData.observe(viewLifecycleOwner) { gradientData ->
            if (gradientData != null) {
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
                binding?.cardInfo?.background = layerDrawable
            }
        }

        binding?.cardInfo?.apply {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val height = height
                    val negativeMargin = (height * 0.9).toInt()

                    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.topMargin = -negativeMargin
                    setLayoutParams(layoutParams)

                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        binding?.cardInfo?.setOnClickListener {
            it.findNavController().popBackStack()
        }

        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_top_view)
        animation.fillAfter = true
        binding?.cardInfo?.startAnimation(animation)

        binding?.copyQr?.setOnClickListener {
            Toast.makeText(requireContext(), "QR is copied", Toast.LENGTH_SHORT).show()
            accountsViewModel.copyToClipBoard(binding?.copyQr?.text.toString())
        }
        binding?.share?.setOnClickListener {
            Toast.makeText(requireContext(), "Share is clicked", Toast.LENGTH_SHORT).show()
        }

        binding?.support?.setOnClickListener {
            Toast.makeText(requireContext(), "Support is clicked", Toast.LENGTH_SHORT).show()
        }
    }
}