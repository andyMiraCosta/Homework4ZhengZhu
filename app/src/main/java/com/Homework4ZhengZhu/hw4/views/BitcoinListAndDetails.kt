package com.Homework4ZhengZhu.hw4.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Homework4ZhengZhu.hw4.data.Bitcoin
import com.Homework4ZhengZhu.hw4.views.screen.PetDetailsScreenContent

@Composable
fun PetListAndDetails(bitcoins: List<Bitcoin>) {
    var currentBitcoin by remember {
        mutableStateOf(bitcoins.first())
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BitcoinList(
            onBitcoinClicked = {
                currentBitcoin = it
            },
            bitcoins = bitcoins,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        PetDetailsScreenContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            bitcoin = currentBitcoin
        )
    }
}