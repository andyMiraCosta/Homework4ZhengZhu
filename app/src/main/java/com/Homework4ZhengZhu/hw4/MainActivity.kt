package com.Homework4ZhengZhu.hw4

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.Homework4ZhengZhu.hw4.di.appModules
import com.Homework4ZhengZhu.hw4.navigation.AppNavigationContent
import com.Homework4ZhengZhu.hw4.navigation.ContentType
import com.Homework4ZhengZhu.hw4.navigation.DeviceFoldPosture
import com.Homework4ZhengZhu.hw4.navigation.NavigationType
import com.Homework4ZhengZhu.hw4.navigation.Screens
import com.Homework4ZhengZhu.hw4.navigation.isBookPosture
import com.Homework4ZhengZhu.hw4.navigation.isSeparating
import com.Homework4ZhengZhu.hw4.ui.theme.ChapterSevenTheme
import com.Homework4ZhengZhu.hw4.views.BitcoinsNavigationDrawer
import com.Homework4ZhengZhu.hw4.views.screen.PetsScreen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deviceFoldingPostureFlow = WindowInfoTracker.getOrCreate(this).windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                when {
                    isBookPosture(foldingFeature) ->
                        DeviceFoldPosture.BookPosture(foldingFeature.bounds)

                    isSeparating(foldingFeature) ->
                        DeviceFoldPosture.SeparatingPosture(
                            foldingFeature.bounds,
                            foldingFeature.orientation
                        )

                    else -> DeviceFoldPosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DeviceFoldPosture.NormalPosture
            )
        setContent {
            val devicePosture = deviceFoldingPostureFlow.collectAsStateWithLifecycle().value
            val windowSizeClass = calculateWindowSizeClass(activity = this)
            val scope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val navController = rememberNavController()

            ChapterSevenTheme {
                val navigationType: NavigationType
                val contentType: ContentType
                when (windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Compact -> {
                        navigationType = NavigationType.BottomNavigation
                        contentType = ContentType.List
                    }

                    WindowWidthSizeClass.Medium -> {
                        navigationType = NavigationType.NavigationRail
                        contentType = if (devicePosture is DeviceFoldPosture.BookPosture
                            || devicePosture is DeviceFoldPosture.SeparatingPosture
                        ) {
                            ContentType.ListAndDetail
                        } else {
                            ContentType.List
                        }
                    }

                    WindowWidthSizeClass.Expanded -> {
                        navigationType = if (devicePosture is DeviceFoldPosture.BookPosture) {
                            NavigationType.NavigationRail
                        } else {
                            NavigationType.NavigationDrawer
                        }
                        contentType = ContentType.ListAndDetail
                    }

                    else -> {
                        navigationType = NavigationType.BottomNavigation
                        contentType = ContentType.List
                    }
                }

                if (navigationType == NavigationType.NavigationDrawer) {
                    PermanentNavigationDrawer(
                        drawerContent = {
                            PermanentDrawerSheet {
                                BitcoinsNavigationDrawer(
                                    onFavoriteClicked = {
                                        navController.navigate(Screens.FavoriteBitcoinsScreen.route)

                                    },
                                    onHomeClicked = {
                                        navController.navigate(Screens.BitcoinsScreen.route)
                                    }
                                )
                            }
                        }
                    ) {
                        AppNavigationContent(
                            navigationType = navigationType,
                            contentType = contentType,
                            onFavoriteClicked = {
                                navController.navigate(Screens.FavoriteBitcoinsScreen.route)
                            },
                            onHomeClicked = {
                                navController.navigate(Screens.BitcoinsScreen.route)
                            },
                            navHostController = navController
                        )
                    }
                } else {
                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet {
                                BitcoinsNavigationDrawer(
                                    onFavoriteClicked = {
                                        navController.navigate(Screens.FavoriteBitcoinsScreen.route)

                                    },
                                    onHomeClicked = {
                                        navController.navigate(Screens.BitcoinsScreen.route)
                                    },
                                    onDrawerClicked = {
                                        scope.launch {

                                            drawerState.close()

                                        }
                                    }
                                )
                            }
                        },
                        drawerState = drawerState
                    ) {
                        AppNavigationContent(
                            navigationType = navigationType,
                            contentType = contentType,
                            onFavoriteClicked = {
                                navController.navigate(Screens.FavoriteBitcoinsScreen.route)
                            },
                            onHomeClicked = {
                                navController.navigate(Screens.BitcoinsScreen.route)
                            },
                            navHostController = navController,
                            onDrawerClicked = {
                                scope.launch {
                                    drawerState.open()
                                    Toast.makeText(this@MainActivity, "Only used when app shown in tablet in vertical mode", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}



/**
 * Using preview with Koin DI .
 * https://medium.com/tech-takeaways/koin-dependent-composable-previews-in-android-jetpack-compose-dd25f57108ec
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainPreview() {

    KoinApplication(
        application = { modules(appModules) }
    ) {
        PetsScreen(
            onBitcoinClicked = {
            },
            contentType = ContentType.List
        )
    }



}
