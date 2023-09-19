package com.example.multimoduleapp

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.navGraphViewModels
import com.example.multimoduleapp.databinding.FragmentTopUpBinding
import com.example.multimoduleapp.viewmodels.SharedViewModel

class TopUpFragment : BaseFragment<FragmentTopUpBinding>(FragmentTopUpBinding::inflate) {
    private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.gradientData.observe(viewLifecycleOwner) { gradientData ->
            if (gradientData != null) {
                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(gradientData.startColor, gradientData.endColor)
                )
                gradientDrawable.cornerRadius = requireContext().dpToPx(gradientData.cornerRadius)
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

        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_top_view)
        animation.fillAfter = true
        binding?.cardInfo?.startAnimation(animation)

        binding?.copyQr?.setOnClickListener {
            Toast.makeText(requireContext(), "QR is copied", Toast.LENGTH_SHORT).show()
        }
        binding?.share?.setOnClickListener {
            Toast.makeText(requireContext(), "Share is clicked", Toast.LENGTH_SHORT).show()
        }

        binding?.support?.setOnClickListener {
            Toast.makeText(requireContext(), "Support is clicked", Toast.LENGTH_SHORT).show()
        }
    }

    fun Context.dpToPx(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale
    }
}