package com.kangengineering.chapterseven

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
import com.kangengineering.chapterseven.di.appModules
import com.kangengineering.chapterseven.navigation.AppNavigationContent
import com.kangengineering.chapterseven.navigation.ContentType
import com.kangengineering.chapterseven.navigation.DeviceFoldPosture
import com.kangengineering.chapterseven.navigation.NavigationType
import com.kangengineering.chapterseven.navigation.Screens
import com.kangengineering.chapterseven.navigation.isBookPosture
import com.kangengineering.chapterseven.navigation.isSeparating
import com.kangengineering.chapterseven.ui.theme.ChapterSevenTheme
import com.kangengineering.chapterseven.views.PetsNavigationDrawer
import com.kangengineering.chapterseven.views.screen.PetsScreen
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
                                PetsNavigationDrawer(
                                    onFavoriteClicked = {
                                        navController.navigate(Screens.FavoritePetsScreen.route)

                                    },
                                    onHomeClicked = {
                                        navController.navigate(Screens.PetsScreen.route)
                                    }
                                )
                            }
                        }
                    ) {
                        AppNavigationContent(
                            navigationType = navigationType,
                            contentType = contentType,
                            onFavoriteClicked = {
                                navController.navigate(Screens.FavoritePetsScreen.route)
                            },
                            onHomeClicked = {
                                navController.navigate(Screens.PetsScreen.route)
                            },
                            navHostController = navController
                        )
                    }
                } else {
                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet {
                                PetsNavigationDrawer(
                                    onFavoriteClicked = {
                                        navController.navigate(Screens.FavoritePetsScreen.route)

                                    },
                                    onHomeClicked = {
                                        navController.navigate(Screens.PetsScreen.route)
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
                                navController.navigate(Screens.FavoritePetsScreen.route)
                            },
                            onHomeClicked = {
                                navController.navigate(Screens.PetsScreen.route)
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
            onPetClicked = {
            },
            contentType = ContentType.List
        )
    }



}
