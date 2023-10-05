package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class LocationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LocationLayout()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun LocationLayout() {
        val mainButtonColor = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = MaterialTheme.colorScheme.surface
        )
        Column(
            Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { findNavController().navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_button),
                            contentDescription = null
                        )
                    }
                },
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
                stringResource(R.string.where_are_you_located), fontSize = 34.sp,
                modifier = Modifier.padding(start = 16.dp), fontWeight = FontWeight.Bold
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF1F2F4)
                ),
                modifier = Modifier
                    .padding(start = 16.dp, top = 28.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp)
                ) {
                    Icon(painterResource(R.drawable.map_pin), contentDescription = null)
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.address_approve_conditions),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.we_can_not_approve),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 20.dp, bottom = 20.dp, end = 20.dp)
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            AutoCompleteTextViewCompose()

            Spacer(Modifier.weight(1f))
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
    fun AutoCompleteTextViewCompose() {
        val context = LocalContext.current

        val autoCompleteTextView = remember {
            AutoCompleteTextView(context).apply {
                val countries = arrayOf(
                    "Afghanistan",
                    "Belarus",
                    "Burundi",
                    "Central African",
                    "Cuba",
                    "Republic of the Congo",
                    "The Democratic Republic Of The Congo",
                    "Côte d`Ivoire",
                    "Hong Kong",
                    "Islamic Republic of Iran",
                    "Iraq",
                    "Lebanon",
                    "Liberia",
                    "Libyan Arab Jamahiriya",
                    "Myanmar",
                    "Nicaragua",
                    "Democratic People's Republic of Korea",
                    "Russian Federation",
                    "Somalia",
                    "Sudan",
                    "South Sudan",
                    "Syrian Arab Republic",
                    "Ukraine",
                    "United States",
                    "Venezuela",
                    "Yemen",
                    "Zimbabwe",
                )
                threshold = 1
                val adapter: ArrayAdapter<String> = ArrayAdapter(
                    context,
                    android.R.layout.simple_dropdown_item_1line, countries
                )
                setAdapter(adapter)
            }
        }

        AndroidView(
            factory = { autoCompleteTextView },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            update = {}
        )
    }
}

