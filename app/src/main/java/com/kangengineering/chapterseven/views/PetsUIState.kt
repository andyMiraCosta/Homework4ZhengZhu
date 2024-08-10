package com.kangengineering.chapterseven.views

import com.kangengineering.chapterseven.data.Cat

data class PetsUIState(
    val isLoading: Boolean = false,
    val pets: List<Cat> = emptyList(),
    val error: String? = null
)
