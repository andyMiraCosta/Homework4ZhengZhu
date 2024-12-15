package com.Homework4ZhengZhu.hw4.views.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.Homework4ZhengZhu.hw4.data.Bitcoin
import com.Homework4ZhengZhu.hw4.navigation.ContentType
import com.Homework4ZhengZhu.hw4.viewmodel.BitcoinsViewModel
import com.Homework4ZhengZhu.hw4.views.BitcoinsScreenContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun PetsScreen(
    onBitcoinClicked: (Bitcoin) -> Unit,
    contentType: ContentType,
) {
    val bitcoinsViewModel: BitcoinsViewModel = koinViewModel()
    val petsUIState by bitcoinsViewModel.bitcoinsUIState.collectAsStateWithLifecycle()
    BitcoinsScreenContent(
        modifier = Modifier
            .fillMaxSize(),
        onPetClicked = onBitcoinClicked,
        contentType = contentType,
        bitcoinsUIState = petsUIState
    )
}