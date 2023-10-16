package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.example.multimoduleapp.ui.viewmodels.SharedViewModel

class TopUpFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.nav_graph)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                TopUpLayout()
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Preview
    @Composable
    fun TopUpLayout() {
        val size = remember { mutableStateOf(IntSize.Zero) }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val supportButtonColor = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF1F2F4),
                contentColor = Color.Black
            )
            val (image, title, descr, serviceRow, walletRow, buttonSupport) = createRefs()
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

            // Content that needs to appear/disappear goes here:
            Image(painter = painterResource(R.drawable.top_up_piece_bg),
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
                    .offset(y = (-0.9f * size.value.height / LocalDensity.current.density).dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        brush = gradientBrush
                    ))

            Text(text = "Top up card",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(title) {
                        top.linkTo(image.bottom, 36.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .offset(y = (-0.9f * size.value.height / LocalDensity.current.density).dp))

            Text(text = "To top up your account, send any amount to the address below",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 32.dp, end = 32.dp)
                    .constrainAs(descr) {
                        top.linkTo(title.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .offset(y = (-0.9f * size.value.height / LocalDensity.current.density).dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(serviceRow) {
                    top.linkTo(descr.bottom, 50.dp)
                    start.linkTo(parent.start, 36.dp)
                    end.linkTo(parent.end, 36.dp)
                    width = Dimension.fillToConstraints
                }
                .offset(y = (-0.9f * size.value.height / LocalDensity.current.density).dp)) {
                Text(text = "Top-up service fee", fontSize = 12.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "2%", fontSize = 14.sp)
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(walletRow) {
                    top.linkTo(serviceRow.bottom, 8.dp)
                    start.linkTo(parent.start, 36.dp)
                    end.linkTo(parent.end, 36.dp)
                    width = Dimension.fillToConstraints
                }
                .offset(y = (-0.9f * size.value.height / LocalDensity.current.density).dp)) {
                Text(text = "Wallet network", fontSize = 12.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "USDT TRC-20", fontSize = 14.sp)
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(buttonSupport) {
                        bottom.linkTo(parent.bottom, 16.dp)
                        start.linkTo(parent.start, 40.dp)
                        end.linkTo(parent.end, 40.dp)
                        width = Dimension.fillToConstraints
                    },
                colors = supportButtonColor,
                onClick = {
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.telegram),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Support",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}




