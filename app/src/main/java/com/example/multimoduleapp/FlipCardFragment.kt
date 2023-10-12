package com.example.multimoduleapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment

class FlipCardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                var cardFace by remember {
                    mutableStateOf(CardFace.Front)
                }
                val mainButtonColor = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = MaterialTheme.colorScheme.surface
                )
                val secondaryButtonColor = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.surface
                )

                FlipCard(
                    cardFace = cardFace,
                    onClick = { cardFace = cardFace.next },
                    modifier = Modifier
                        .fillMaxSize(),
                    front = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.card_palette_bg),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds
                            )

                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, top = 30.dp),
                                colors = mainButtonColor,
                                onClick = {
//                                    findNavController().navigate(R.id.action_getAcquaintedFragment_to_locationFragment)
                                }
                            ) {
                                Text(
                                    text = "Top up card",
                                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                                    fontSize = 16.sp
                                )
                            }

                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp),
                                colors = secondaryButtonColor,
                                onClick = {
//                                    findNavController().navigate(R.id.action_getAcquaintedFragment_to_locationFragment)
                                }
                            ) {
                                Text(
                                    text = "Payments",
                                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    },
                    back = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Blue),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Back",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                )
            }
        }
    }

    enum class CardFace(val angle: Float) {
        Front(0f) {
            override val next: CardFace
                get() = Back
        },
        Back(180f) {
            override val next: CardFace
                get() = Front
        };

        abstract val next: CardFace
    }

    enum class RotationAxis {
        AxisX,
        AxisY,
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FlipCard(
        cardFace: CardFace,
        onClick: (CardFace) -> Unit,
        modifier: Modifier = Modifier,
        axis: RotationAxis = RotationAxis.AxisY,
        back: @Composable () -> Unit = {},
        front: @Composable () -> Unit = {},
    ) {
        val rotation = animateFloatAsState(
            targetValue = cardFace.angle,
            animationSpec = tween(
                durationMillis = 400,
                easing = FastOutSlowInEasing,
            ), label = ""
        )
        Card(
            onClick = { onClick(cardFace) },
            modifier = modifier
                .graphicsLayer {
                    if (axis == RotationAxis.AxisX) {
                        rotationX = rotation.value
                    } else {
                        rotationY = rotation.value
                    }
                    cameraDistance = 12f * density
                },
        ) {
            if (rotation.value <= 90f) {
                Box(
                    Modifier.fillMaxSize()
                ) {
                    front()
                }
            } else {
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            if (axis == RotationAxis.AxisX) {
                                rotationX = 180f
                            } else {
                                rotationY = 180f
                            }
                        },
                ) {
                    back()
                }
            }
        }
    }
}



