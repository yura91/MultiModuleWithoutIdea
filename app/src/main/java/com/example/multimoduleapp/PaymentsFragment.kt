package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.multimoduleapp.model.HistoryItem
import com.example.multimoduleapp.model.HistoryPaymentModel

class PaymentsFragment : Fragment() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val historyItems1: MutableList<HistoryItem> = mutableListOf()
        val historyPayments: MutableList<HistoryPaymentModel> = mutableListOf()
        historyItems1.add(HistoryItem("Top up", "+ 100,00 €", "23:59"))
        historyItems1.add(HistoryItem("Netflix", "- 8,57 €", "23:55"))
        historyItems1.add(HistoryItem("Apple TV+", "- 12,99 €", "6:15"))
        historyItems1.add(HistoryItem("Amazon", "- 12,99 €", "1:01"))
        historyItems1.add(HistoryItem("PS Store", "+ 100,00 €", "23:59"))
        historyPayments.add(HistoryPaymentModel("Today", historyItems1))
        val historyItems2: MutableList<HistoryItem> = mutableListOf()
        historyItems2.add(HistoryItem("Top up", "+ 100,00 €", "23:59"))
        historyItems2.add(HistoryItem("Netflix", "- 8,57 €", "23:55"))
        historyItems2.add(HistoryItem("Apple TV+", "- 12,99 €", "6:15"))
        historyItems2.add(HistoryItem("Amazon", "- 12,99 €", "1:01"))
        historyPayments.add(HistoryPaymentModel("Yesterday", historyItems2))

        val historyItems3: MutableList<HistoryItem> = mutableListOf()
        historyItems3.add(HistoryItem("Zepliin", "- 8,57 €", "18:55"))
        historyItems3.add(HistoryItem("Miro", "- 12,99 €", "9:55"))
        historyItems3.add(HistoryItem("Apple TV+", "- 12,99 €", "6:15"))
        historyItems3.add(HistoryItem("Amazon", "- 12,99 €", "1:01"))
        historyPayments.add(HistoryPaymentModel("10 May", historyItems3))

        val historyItems4: MutableList<HistoryItem> = mutableListOf()
        historyItems4.add(HistoryItem("Zepliin", "- 8,57 €", "18:55"))
        historyItems4.add(HistoryItem("Miro", "- 12,99 €", "9:55"))
        historyItems4.add(HistoryItem("Apple TV+", "- 12,99 €", "6:15"))
        historyItems4.add(HistoryItem("Amazon", "- 12,99 €", "1:01"))
        historyPayments.add(HistoryPaymentModel("8 May", historyItems3))

        val historyItems5: MutableList<HistoryItem> = mutableListOf()
        historyItems5.add(HistoryItem("Zepliin", "- 8,57 €", "18:55"))
        historyItems5.add(HistoryItem("Miro", "- 12,99 €", "9:55"))
        historyPayments.add(HistoryPaymentModel("5 May", historyItems5))

        val historyItems6: MutableList<HistoryItem> = mutableListOf()
        historyItems6.add(HistoryItem("Zepliin", "- 8,57 €", "18:55"))
        historyItems6.add(HistoryItem("Miro", "- 12,99 €", "9:55"))
        historyItems6.add(HistoryItem("Apple TV+", "- 12,99 €", "6:15"))
        historyPayments.add(HistoryPaymentModel("3 May", historyItems6))

        return ComposeView(requireContext()).apply {
            setContent {
                Column {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Payments",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { findNavController().navigateUp() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.back_button),
                                    contentDescription = null
                                )
                            }
                        }
                    )
                    CategorizedLazyColumn(
                        categories = historyPayments
                    )
                }
            }
        }
    }

    @Composable
    private fun CategoryHeader(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        )
    }

    @Composable
    private fun CategoryItem(
        type: String,
        time: String,
        operationValue: String,
        modifier: Modifier = Modifier
    ) {
        Column {
            Row {
                Text(
                    text = type,
                    fontSize = 14.sp,
                    modifier = modifier
                        .wrapContentSize()
                        .background(Color.White)
                        .padding(top = 16.dp, start = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = operationValue,
                    fontSize = 14.sp,
                    modifier = modifier
                        .wrapContentSize()
                        .background(Color.White)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
            }

            Text(
                text = time,
                fontSize = 14.sp,
                modifier = modifier
                    .wrapContentSize()
                    .background(Color.White)
                    .padding(start = 16.dp)
            )
        }
    }

    @Composable
    private fun CategorizedLazyColumn(
        categories: List<HistoryPaymentModel>,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(
            modifier = modifier
        ) {
            categories.forEach { category ->
                item {
                    CategoryHeader(category.title)
                }
                items(category.historyItems) { text ->
                    CategoryItem(text.operationType, text.operationTime, text.operationValue)
                }
            }
        }
    }
}
