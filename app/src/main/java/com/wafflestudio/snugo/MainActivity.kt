package com.wafflestudio.snugo

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnPreDrawListener
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.naver.maps.geometry.LatLng
import com.wafflestudio.snugo.features.arrivaldetail.ArrivalDetailScreen
import com.wafflestudio.snugo.features.home.HomePageMode
import com.wafflestudio.snugo.features.home.HomeScreen
import com.wafflestudio.snugo.features.onboarding.SignInScreen
import com.wafflestudio.snugo.features.onboarding.UserViewModel
import com.wafflestudio.snugo.features.records.RecordsScreen
import com.wafflestudio.snugo.features.settings.SettingsScreen
import com.wafflestudio.snugo.location.LocationProvider
import com.wafflestudio.snugo.location.getLocationPermissions
import com.wafflestudio.snugo.navigation.BottomNavigation
import com.wafflestudio.snugo.navigation.BottomNavigationItem
import com.wafflestudio.snugo.navigation.NavigationDestination
import com.wafflestudio.snugo.service.LocationService
import com.wafflestudio.snugo.ui.theme.SnugoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var locationProvider: LocationProvider

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        getLocationPermissions()

        var loadFinished = false
        val startDestination =
            userViewModel.accessToken.filterNotNull().map {
                loadFinished = true
                if (it.isNotEmpty()) {
                    NavigationDestination.Main.route
                } else {
                    NavigationDestination.Onboarding.route
                }
            }

        setContent {
            SnugoTheme {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val showBottomNavigation =
                    BottomNavigationItem.items.map { it.destination.route }
                        .contains(backStackEntry?.destination?.route)
                val animatedOffsetDp by animateIntAsState(
                    targetValue = if (showBottomNavigation) 0 else 80,
                    label = "bottom navigation offset dp",
                )

                val pathFlow = remember { mutableStateOf<Flow<List<LatLng>>?>(null) }
                val paths = pathFlow.value?.collectAsState(emptyList())
                var homePageMode by remember {
                    mutableStateOf(HomePageMode.NORMAL)
                }
                val startDestinationState by startDestination.collectAsState(initial = NavigationDestination.Onboarding.route)

                CompositionLocalProvider(
                    LocalNavController provides navController,
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = startDestinationState,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            navigation(
                                startDestination = NavigationDestination.SignIn.route,
                                route = NavigationDestination.Onboarding.route,
                            ) {
                                slideVerticalComposable(
                                    route = NavigationDestination.SignIn.route,
                                ) {
                                    SignInScreen()
                                }
                            }

                            navigation(
                                startDestination = NavigationDestination.Home.route,
                                route = NavigationDestination.Main.route,
                            ) {
                                bottomNavComposable(
                                    route = NavigationDestination.Home.route,
                                ) {
                                    HomeScreen(
                                        modifier = Modifier.padding(bottom = (80 - animatedOffsetDp).dp),
                                        pageMode = homePageMode,
                                        paths?.value ?: emptyList(),
                                        startMoving = {
                                            homePageMode = HomePageMode.MOVING
                                            pathFlow.value = locationProvider.subscribePath()
                                        },
                                    )
                                }

                                slideVerticalComposable(
                                    route = NavigationDestination.ArrivalDetail.route,
                                ) {
                                    ArrivalDetailScreen()
                                }

                                bottomNavComposable(
                                    route = NavigationDestination.Records.route,
                                ) {
                                    RecordsScreen(
                                        modifier = Modifier.padding(bottom = (80 - animatedOffsetDp).dp),
                                    )
                                }

                                bottomNavComposable(
                                    route = NavigationDestination.Settings.route,
                                ) {
                                    SettingsScreen(
                                        modifier = Modifier.padding(bottom = (80 - animatedOffsetDp).dp),
                                    )
                                }
                            }
                        }
                        BottomNavigation(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .offset {
                                        IntOffset(
                                            x = 0,
                                            y =
                                                animatedOffsetDp.dp
                                                    .toPx()
                                                    .roundToInt(),
                                        )
                                    },
                            navController = navController,
                        )
                    }
                }
            }
        }

        val rootView =
            window.decorView
                .findViewById<ViewGroup>(android.R.id.content)
                .getChildAt(0) as ComposeView
        rootView.viewTreeObserver.addOnPreDrawListener(
            object : OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (loadFinished) {
                        rootView.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            },
        )
    }
}

fun NavGraphBuilder.bottomNavComposable(
    route: String,
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit),
) {
    composable(
        route = route,
        enterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        content = content,
    )
}

fun NavGraphBuilder.slideVerticalComposable(
    route: String,
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit),
) {
    composable(
        route = route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
            ) + fadeIn()
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
            ) + fadeOut()
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            fadeIn()
        },
        content = content,
    )
}

fun NavController.navigateAsOrigin(route: String) {
    navigate(route) {
        currentBackStackEntry?.destination?.parent?.startDestinationRoute?.let {
            popUpTo(it) {
                inclusive = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun isServiceRunning(context: Context): Boolean {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (LocationService::class.java.name == service.service.className) {
            return true
        }
    }
    return false
}
