package com.example.multimoduleapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wajahatkarim.flippable.FlipAnimationType
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.rememberFlipController

class FlipCardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val mainButtonColor = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = MaterialTheme.colorScheme.surface
                )
                val secondaryButtonColor = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.surface
                )
                val controller = rememberFlipController()

                Flippable(
                    frontSide = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .background(Color.White),
                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                                        .clip(RoundedCornerShape(30.dp))
                                        .clickable {
                                            controller.flipToBack()
                                        },
                                    painter = painterResource(id = R.drawable.front_side_bg),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds
                                )

                                Text(
                                    text = "3466",
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(end = 32.dp, bottom = 64.dp),
                                    fontSize = 16.sp,
                                    color = Color.White
                                )

                                Text(
                                    text = "02/24",
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(end = 32.dp, bottom = 32.dp),
                                    fontSize = 16.sp,
                                    color = Color.White
                                )

                                Text(
                                    text = "JOHN DOE",
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 32.dp, bottom = 64.dp),
                                    fontSize = 16.sp,
                                    color = Color.White
                                )

                                Text(
                                    text = "0,00 €",
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 24.dp, bottom = 112.dp),
                                    fontSize = 46.sp,
                                    color = Color.White
                                )
                            }

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
                                    findNavController().navigate(R.id.action_flipCardFragment_to_paymentsFragment)
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

                    backSide = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .background(Color.White),
                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(start = 4.dp, end = 4.dp, top = 4.dp),
                                    painter = painterResource(id = R.drawable.back_side_bg),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds
                                )

                                Text(
                                    text = "02/24",
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 24.dp, bottom = 28.dp),
                                    fontSize = 22.sp,
                                    color = Color.White
                                )

                                Image(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 96.dp, bottom = 28.dp)
                                        .clickable {
//                                            controller.flipToBack()
                                        },
                                    painter = painterResource(id = R.drawable.copy),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds
                                )

                                Text(
                                    text = "Expire date",
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 24.dp, bottom = 60.dp),
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "173",
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(end = 126.dp, bottom = 28.dp),
                                    fontSize = 22.sp,
                                    color = Color.White
                                )

                                Image(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(end = 92.dp, bottom = 28.dp)
                                        .clickable {
//                                            controller.flipToBack()
                                        },
                                    painter = painterResource(id = R.drawable.copy),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds
                                )

                                Text(
                                    text = "CVV",
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(end = 129.dp, bottom = 60.dp),
                                    fontSize = 16.sp,
                                    color = Color.White
                                )

                                Text(
                                    text = "1234 5678 9012 3456",
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 24.dp, bottom = 114.dp),
                                    fontSize = 22.sp,
                                    color = Color.White
                                )

                                Image(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(end = 73.dp, bottom = 114.dp)
                                        .clickable {
//                                            controller.flipToBack()
                                        },
                                    painter = painterResource(id = R.drawable.copy),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds
                                )

                                Text(
                                    text = "Card number",
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 24.dp, bottom = 146.dp),
                                    fontSize = 16.sp,
                                    color = Color.White
                                )

                                Text(
                                    text = "JOHN DOE",
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 24.dp, bottom = 200.dp),
                                    fontSize = 22.sp,
                                    color = Color.White
                                )

                                Image(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 149.dp, bottom = 200.dp)
                                        .clickable {
//                                            controller.flipToBack()
                                        },
                                    painter = painterResource(id = R.drawable.copy),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds
                                )

                                Text(
                                    text = "Name",
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 24.dp, bottom = 232.dp),
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                            ) {
                                Button(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .align(Alignment.Center),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                    onClick = {
//                                    findNavController().navigate(R.id.action_getAcquaintedFragment_to_locationFragment)
                                        controller.flipToFront()
                                    }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.back),
                                        modifier = Modifier.padding(
                                            start = 22.dp,
                                            end = 22.dp,
                                            top = 22.dp,
                                            bottom = 22.dp
                                        ),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    },

                    flipController = controller,

                    modifier = Modifier,

                    flipDurationMs = 400,

                    flipOnTouch = false,

                    flipEnabled = true,

                    contentAlignment = Alignment.TopCenter,

                    autoFlip = false,

                    autoFlipDurationMs = 1000,

                    flipAnimationType = FlipAnimationType.HORIZONTAL_CLOCKWISE,

                    cameraDistance = 30.0F,

                    onFlippedListener = { currentSide ->
                    }
                )
            }
        }
    }
}



