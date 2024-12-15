package com.Homework4ZhengZhu.hw4.navigation


sealed class Screens(val route: String) {
    object BitcoinsScreen : Screens("bitcoins")
    object BitcoinDetailsScreen : Screens("bitcoinDetails")
    object FavoriteBitcoinsScreen : Screens("favoriteBitcoins")
}