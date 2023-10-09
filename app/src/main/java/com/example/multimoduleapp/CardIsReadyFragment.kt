package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.navGraphViewModels
import com.example.multimoduleapp.ui.viewmodels.SharedViewModel


class CardIsReadyFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.nav_graph)
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
        val startColor by sharedViewModel.startColor.observeAsState()
        val endColor by sharedViewModel.endColor.observeAsState()

        val gradientBrush = if (startColor != null && endColor != null) {
            Brush.verticalGradient(
                colors = listOf(startColor!!, endColor!!)
            )
        } else {
            Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Transparent)
            )
        }
        Image(
            painter = painterResource(R.drawable.card_palette_bg),
            contentDescription = "Your Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    size.value = coordinates.size
                    val positionInWindow = coordinates.positionInWindow()
                    // Используйте size и positionInWindow
                }
                .offset(y = (-0.4f * size.value.height / LocalDensity.current.density).dp)
                .padding(start = 4.dp, end = 4.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(
                    brush = gradientBrush
                )
        )
    }
}


