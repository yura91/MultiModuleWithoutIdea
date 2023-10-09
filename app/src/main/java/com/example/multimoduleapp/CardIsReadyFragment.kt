package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment


class CardIsReadyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                CardIsReadyLayout()
            }
        }
    }

    @Preview
    @Composable
    fun CardIsReadyLayout() {
        val size = remember { mutableStateOf(IntSize.Zero) }
        Image(
            painter = painterResource(R.drawable.card_palette_bg),
            contentDescription = "Your Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    size.value = coordinates.size
                    val positionInWindow = coordinates.positionInWindow()
                    // Используйте size и positionInWindow
                }
                .offset(y = (-0.4f * size.value.height / LocalDensity.current.density).dp)
        )
    }
}


