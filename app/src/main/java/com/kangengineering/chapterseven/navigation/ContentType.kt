package com.kangengineering.chapterseven.navigation

sealed interface ContentType {
    object List : ContentType
    object ListAndDetail : ContentType
}