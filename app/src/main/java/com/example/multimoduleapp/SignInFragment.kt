package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment

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

    @Composable
    fun SignInLayout() {
        ConstraintLayout {
            val (redBox, blueBox, yellowBox, text) = createRefs()

            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Red)
                .constrainAs(redBox) {})

            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Blue)
                .constrainAs(blueBox) {
                    top.linkTo(redBox.bottom)
                    start.linkTo(redBox.end)
                })

            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Yellow)
                .constrainAs(yellowBox) {
                    bottom.linkTo(blueBox.bottom)
                    start.linkTo(blueBox.end, 20.dp)
                })

            Text("Hello World", modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top)
                start.linkTo(yellowBox.start)
            })
        }
    }
}