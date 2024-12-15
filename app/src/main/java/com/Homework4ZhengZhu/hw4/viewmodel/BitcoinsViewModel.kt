package com.Homework4ZhengZhu.hw4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Homework4ZhengZhu.hw4.data.NetworkResult
import com.Homework4ZhengZhu.hw4.data.BitcoinsRepository
import com.Homework4ZhengZhu.hw4.views.BitcoinsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BitcoinsViewModel(
    private val bitcoinsRepository: BitcoinsRepository
): ViewModel() {
    val bitcoinsUIState = MutableStateFlow(BitcoinsUIState())

    init {
        getBitcoins()
    }

    private fun getBitcoins() {
        bitcoinsUIState.value = BitcoinsUIState(isLoading = true)
        viewModelScope.launch {
            when (val result = bitcoinsRepository.getBitcoins()) {
                is NetworkResult.Success -> {
                    bitcoinsUIState.update {
                        it.copy(isLoading = false, bitcoins = result.data)
                    }
                }
                is NetworkResult.Error -> {
                    bitcoinsUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }
}