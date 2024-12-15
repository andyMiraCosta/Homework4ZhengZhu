package com.Homework4ZhengZhu.hw4.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Homework4ZhengZhu.hw4.data.Bitcoin
import com.Homework4ZhengZhu.hw4.navigation.ContentType

@Composable
fun BitcoinsScreenContent(
    modifier: Modifier,
    onPetClicked: (Bitcoin) -> Unit,
    contentType: ContentType,
    bitcoinsUIState: BitcoinsUIState
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = bitcoinsUIState.isLoading
        ) {
            CircularProgressIndicator()
        }

        AnimatedVisibility(
            visible = bitcoinsUIState.bitcoins.isNotEmpty()
        ) {
            if (contentType == ContentType.List) {
                BitcoinList(
                    onBitcoinClicked = onPetClicked,
                    bitcoins = bitcoinsUIState.bitcoins,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                PetListAndDetails(
                    bitcoins = bitcoinsUIState.bitcoins
                )
            }

        }
        AnimatedVisibility(
            visible = bitcoinsUIState.error != null
        ) {
            Text(text = bitcoinsUIState.error ?: "")
        }
    }
}