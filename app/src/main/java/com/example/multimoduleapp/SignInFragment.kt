package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.Fragment
import com.example.multimoduleapp.ui.theme.MyApp

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SignInLayout()
            }
        }
    }

    @Preview
    @Composable
    fun SignInLayout() {
        MyApp {
            val mainButtonColor = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = MaterialTheme.colorScheme.surface
            )
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                val (best, signApple, signGoogle, text) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.the_best_v_cards),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .constrainAs(best) {
                            top.linkTo(parent.top, 4.dp)
                            bottom.linkTo(signApple.top, 44.dp)
                            start.linkTo(parent.start, 4.dp)
                            end.linkTo(parent.end, 4.dp)
                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        }
                )

                Button(
                    colors = mainButtonColor,
                    onClick = { },
                    modifier = Modifier
                        .constrainAs(signApple) {
                            bottom.linkTo(signGoogle.top, 8.dp)
                            start.linkTo(parent.start, margin = 40.dp)
                            end.linkTo(parent.end, margin = 40.dp)
                            width = Dimension.fillToConstraints
                        },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.apple_sign),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sign in with Apple",
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                            fontSize = 16.sp
                        )
                    }
                }

                Button(
                    colors = mainButtonColor,
                    onClick = { },
                    modifier = Modifier
                        .constrainAs(signGoogle) {
                            bottom.linkTo(text.top, 36.dp)
                            start.linkTo(parent.start, 40.dp)
                            end.linkTo(parent.end, 40.dp)
                            width = Dimension.fillToConstraints
                        },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_sign),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sign in with Google",
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                            fontSize = 16.sp
                        )
                    }
                }

                Text(
                    "By registering, you agree to our\n" + "Privacy Policy and Terms of use",
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(text) {
                            bottom.linkTo(parent.bottom, 34.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                    lineHeight = 12.sp, fontSize = 12.sp
                )
            }
        }
    }
}