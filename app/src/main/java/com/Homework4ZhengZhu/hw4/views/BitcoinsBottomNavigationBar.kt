package com.Homework4ZhengZhu.hw4.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.Homework4ZhengZhu.hw4.navigation.Screens

@Composable
fun BitcoinsBottomNavigationBar(
    onFavoriteClicked: () -> Unit,
    onHomeClicked: () -> Unit
) {
    val items = listOf(Screens.BitcoinsScreen, Screens.FavoriteBitcoinsScreen)
    val selectedItem = remember { mutableStateOf(items[0]) }
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        NavigationBarItem(
            selected = selectedItem.value == Screens.BitcoinsScreen,
            onClick = {
                onHomeClicked()
                selectedItem.value = Screens.BitcoinsScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon"
                )
            }
        )

        NavigationBarItem(
            selected = selectedItem.value == Screens.FavoriteBitcoinsScreen,
            onClick = {
                onFavoriteClicked()
                selectedItem.value = Screens.FavoriteBitcoinsScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon"
                )
            }
        )
    }
}