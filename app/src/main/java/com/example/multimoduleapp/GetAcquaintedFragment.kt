package com.example.multimoduleapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var birthDate by remember { mutableStateOf("") }
        val dialogShowState = remember { mutableStateOf(false) }

        if (dialogShowState.value) {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Создаем DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                LocalContext.current, // Получаем текущий контекст
                { _, year, month, dayOfMonth ->
                    val date = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }
                    birthDate =
                        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date.time)
                    dialogShowState.value = false
                },
                year,
                month,
                day
            )

            datePickerDialog.setOnCancelListener {
                dialogShowState.value = false
            }

            // Отображаем DatePickerDialog
            LaunchedEffect(Unit) {
                datePickerDialog.show()
            }
        }

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
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 28.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                label = { Text("First name") }
            )

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 28.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                label = { Text("Last name") }
            )

            TextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Search Icon",
                        modifier = Modifier.clickable {
                            dialogShowState.value = true
                        }
                    )
                },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 28.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                label = { Text("Birth date") }
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