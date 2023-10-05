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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment


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
            ColorSeekBar(
                colors = listOf(Color.Red, Color.Green, Color.Blue),
                onColorChanged = { color -> println("Selected color: $color") }
            )
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
        colors: List<Color>,
        onColorChanged: (Color) -> Unit
    ) {
        var selectedColor by remember { mutableStateOf(colors.first()) }
        Slider(
            modifier = Modifier.height(100.dp),
            value = colors.indexOf(selectedColor).toFloat(),
            onValueChange = { newValue ->
                selectedColor = colors[newValue.toInt()]
                onColorChanged(selectedColor)
            },
            valueRange = 0f..(colors.size - 1).toFloat(),
            steps = colors.size - 1,
            colors = SliderDefaults.colors(
                thumbColor = selectedColor
            )
        )
    }

    @Preview
    @Composable
    fun PreviewColorSeekBar() {
        ColorSeekBar(
            colors = listOf(Color.Red, Color.Green, Color.Blue),
            onColorChanged = { color -> println("Selected color: $color") }
        )
    }
}
