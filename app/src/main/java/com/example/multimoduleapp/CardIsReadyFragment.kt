package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        val mainButtonColor = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = MaterialTheme.colorScheme.surface
        )

        val gradientBrush = if (startColor != null && endColor != null) {
            Brush.verticalGradient(
                colors = listOf(startColor!!, endColor!!)
            )
        } else {
            Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Transparent)
            )
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val (image, text1, text2, button, securedText, bottomRow) = createRefs()
            Image(
                painter = painterResource(R.drawable.card_palette_bg),
                contentDescription = "Your Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .constrainAs(image) {
                        top.linkTo(parent.top, 4.dp)
                        start.linkTo(parent.start, 4.dp)
                        end.linkTo(parent.end, 4.dp)
                        width = Dimension.fillToConstraints
                    }
                    .onGloballyPositioned { coordinates ->
                        size.value = coordinates.size
                        val positionInWindow = coordinates.positionInWindow()
                        // Используйте size и positionInWindow
                    }
                    .offset(y = (-0.4f * size.value.height / LocalDensity.current.density).dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        brush = gradientBrush
                    )
            )

            Text(text = "Billed Monthly",
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-0.4f * size.value.height / LocalDensity.current.density).dp)
                    .constrainAs(text1) {
                        top.linkTo(image.bottom, 40.dp)
                        start.linkTo(parent.start, 40.dp)
                        end.linkTo(parent.end, 40.dp)
                        width = Dimension.fillToConstraints
                    })

            Text(text = "Billed Annually",
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-0.4f * size.value.height / LocalDensity.current.density).dp)
                    .constrainAs(text2) {
                        top.linkTo(text1.bottom, 40.dp)
                        start.linkTo(parent.start, 40.dp)
                        end.linkTo(parent.end, 40.dp)
                        width = Dimension.fillToConstraints
                    })

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-0.4f * size.value.height / LocalDensity.current.density).dp)
                    .constrainAs(button) {
                        top.linkTo(text2.bottom, 40.dp)
                        start.linkTo(parent.start, 40.dp)
                        end.linkTo(parent.end, 40.dp)
                        width = Dimension.fillToConstraints
                    },
                colors = mainButtonColor,
                onClick = {
                    findNavController().navigate(R.id.action_cardIsReadyFragment_to_flipCardFragment)
                }
            ) {
                Text(
                    text = stringResource(R.string.next),
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    fontSize = 16.sp
                )
            }

            Text(text = "Secured by the App Store. Cancel anytime",
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-0.4f * size.value.height / LocalDensity.current.density).dp)
                    .constrainAs(securedText) {
                        top.linkTo(button.bottom, 10.dp)
                        start.linkTo(parent.start, 40.dp)
                        end.linkTo(parent.end, 40.dp)
                        width = Dimension.fillToConstraints
                    })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomRow) {
                        bottom.linkTo(parent.bottom, 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Terms of Service")
                Text(text = "Restore", modifier = Modifier.padding(start = 20.dp))
                Text(text = "Privacy Policy", modifier = Modifier.padding(start = 20.dp))
            }
        }
    }
}


