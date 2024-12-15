package com.Homework4ZhengZhu.hw4.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.Homework4ZhengZhu.hw4.views.BitcoinsBottomNavigationBar
import com.Homework4ZhengZhu.hw4.views.BitcoinsNavigationRail

@Composable
fun AppNavigationContent(
    contentType: ContentType,
    navigationType: NavigationType,
    onFavoriteClicked: () -> Unit,
    onHomeClicked: () -> Unit,
    navHostController: NavHostController,
    onDrawerClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        AnimatedVisibility(
            visible = navigationType == NavigationType.NavigationRail
        ) {
            BitcoinsNavigationRail(
                onFavoriteClicked = onFavoriteClicked,
                onHomeClicked = onHomeClicked,
                onDrawerClicked = onDrawerClicked
            )
        }
        Scaffold(
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    AppNavigation(
                        contentType = contentType,
                        navHostController = navHostController
                    )
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = navigationType == NavigationType.BottomNavigation
                ) {
                    BitcoinsBottomNavigationBar(
                        onFavoriteClicked = onFavoriteClicked,
                        onHomeClicked = onHomeClicked
                    )
                }
            }
        )
    }
}