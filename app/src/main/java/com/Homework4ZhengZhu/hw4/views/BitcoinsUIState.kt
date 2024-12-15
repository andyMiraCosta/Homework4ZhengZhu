package com.Homework4ZhengZhu.hw4.views

import com.Homework4ZhengZhu.hw4.data.Bitcoin

data class BitcoinsUIState(
    val isLoading: Boolean = false,
    val bitcoins: List<Bitcoin> = emptyList(),
    val error: String? = null
)
