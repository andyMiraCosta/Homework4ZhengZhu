package com.Homework4ZhengZhu.hw4.navigation

sealed interface ContentType {
    object List : ContentType
    object ListAndDetail : ContentType
}