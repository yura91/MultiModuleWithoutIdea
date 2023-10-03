package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment

class GetAcquaintedFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                GetAcquaintedLayout()
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun GetAcquaintedLayout() {
        var text1 by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf("") }
        var text3 by remember { mutableStateOf("") }
        val mainButtonColor = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = MaterialTheme.colorScheme.surface
        )

        Column(
            Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.more),
                            contentDescription = null
                        )
                    }
                }
            )

            Text(
                "Let's get\n" +
                        "acquainted", fontSize = 34.sp,
                modifier = Modifier.padding(start = 16.dp), fontWeight = FontWeight.Bold
            )

            TextField(
                value = text1,
                onValueChange = { text1 = it },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 28.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                label = { Text("Enter text") }
            )

            TextField(
                value = text2,
                onValueChange = { text2 = it },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 28.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                label = { Text("Enter text") }
            )

            TextField(
                value = text3,
                onValueChange = { text3 = it },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 28.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                label = { Text("Enter text") }
            )
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                colors = mainButtonColor,
                onClick = {}
            ) {
                Text(
                    text = "Continue",
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}