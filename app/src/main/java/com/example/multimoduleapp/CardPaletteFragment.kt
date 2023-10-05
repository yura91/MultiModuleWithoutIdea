package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.rtugeek.android.colorseekbar.ColorSeekBar


class CardPaletteFragment : Fragment() {

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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.card_palette_bg),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(16.dp))
            ColorSeekBar(0) {}
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                colors = mainButtonColor,
                onClick = {
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

    @Composable
    fun ColorSeekBar(
        progress: Int,
        onProgressChanged: (Int) -> Unit
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
        AndroidView(
            factory = { colorSeekBar },
            update = { view ->
                colorSeekBar.setProgress(progress)
            }
        )
    }
}
