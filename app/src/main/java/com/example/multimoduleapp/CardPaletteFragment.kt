package com.example.multimoduleapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.multimoduleapp.ui.viewmodels.CardPaletteViewModel
import com.rtugeek.android.colorseekbar.ColorSeekBar


class CardPaletteFragment : Fragment() {
    private val gradientOffset = 25.0F
    private val cardPalleteViewModel by viewModels<CardPaletteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                CardPaletteLayout()
            }
        }
    }

    @Preview
    @Composable
    fun CardPaletteLayout() {
        val mainButtonColor = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = MaterialTheme.colorScheme.surface
        )
        val startColor by cardPalleteViewModel.startColor.observeAsState()
        val endColor by cardPalleteViewModel.selectedColor.observeAsState()

        val gradientBrush = if (startColor != null && endColor != null) {
            Brush.verticalGradient(
                colors = listOf(startColor!!, endColor!!)
            )
        } else {
            Brush.verticalGradient(
                colors = listOf(Color(2147483647), Color(-312545))
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.card_palette_bg),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        brush = gradientBrush
                    ),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(16.dp))
            ColorSeekBar(0)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                colors = mainButtonColor,
                onClick = {
                    findNavController().navigate(R.id.action_cardPaletteFragment_to_cardIsReadyFragment)
                }
            ) {
                Text(
                    text = stringResource(R.string.next),
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    fontSize = 16.sp
                )
            }
        }
    }

    private fun pickColor(colorSeekBar: ColorSeekBar, progress: Int): Int {
        colorSeekBar.let { colorSeekBar ->
            if (progress < 0) return Int.MAX_VALUE
            if (progress > colorSeekBar.maxProgress) return Int.MAX_VALUE
            return if (progress >= colorSeekBar.colors.size) Int.MAX_VALUE else colorSeekBar.colors[progress]
        }
        return Int.MAX_VALUE
    }

    fun Context.dpToPx(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale
    }

    @Composable
    fun ColorSeekBar(
        progress: Int,
    ) {
        val colorSeekBar =
            this.layoutInflater.inflate(R.layout.widget_color_seekbar, null) as ColorSeekBar
        val colors: IntArray = intArrayOf(
            ContextCompat.getColor(requireContext(), R.color.red_gr_color),
            ContextCompat.getColor(requireContext(), R.color.third_gr_color),
            ContextCompat.getColor(requireContext(), R.color.fourth_gr_color),
            ContextCompat.getColor(requireContext(), R.color.fifth_gr_color),
            ContextCompat.getColor(requireContext(), R.color.red_gr_color)
        )

        colorSeekBar.setColorSeeds(colors)
        colorSeekBar.setOnColorChangeListener { progress, color ->
            val endColor = color
            val colorPosition = progress - requireContext().dpToPx(gradientOffset)
            val startColor = pickColor(colorSeekBar, colorPosition.toInt())
            cardPalleteViewModel.setColors(Color(startColor), Color(endColor))
        }

        AndroidView(
            factory = { colorSeekBar },
            update = { view ->
                colorSeekBar.setProgress(progress)
            }
        )
    }
}
