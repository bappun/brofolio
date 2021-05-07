package com.bappun.brofolio

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.bappun.brofolio.data.Account
import com.bappun.brofolio.data.Asset
import com.bappun.brofolio.screens.Accounts
import com.bappun.brofolio.screens.Home
import com.bappun.brofolio.ui.theme.BroFolioTheme
import com.bappun.brofolio.viewmodels.PortfolioViewModel

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Filled.Home)
    object Accounts : Screen("accounts", R.string.accounts, Icons.Filled.ManageAccounts)
}

@ExperimentalFoundationApi
@Composable
fun Activity(context: Context, portfolioViewModel: PortfolioViewModel = PortfolioViewModel()) {

    val accounts: List<Account> by portfolioViewModel.accounts.observeAsState(listOf<Account>())
    val total: Float by portfolioViewModel.total.observeAsState(0.0F)
    val assets: List<Asset> by portfolioViewModel.assets.observeAsState(listOf<Asset>())

    portfolioViewModel.loadAccounts(context)
    portfolioViewModel.update()

    BroFolioTheme {
        // A surface container using the 'background' color from the theme

        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            val items = listOf(
                Screen.Home,
                Screen.Accounts,
            )
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

            Scaffold(
                floatingActionButton = {
                    if (currentRoute == Screen.Accounts.route) {
                        FloatingActionButton(onClick = {
                            Account.create(context, "binance", "xxx", "xxx")?.let { portfolioViewModel.addAccount(it) }
                        }) {
                            Icon(Icons.Filled.Add, "add")
                        }
                    } else {
                        FloatingActionButton(onClick = {
                            portfolioViewModel.update()
                        }) {
                            Icon(Icons.Filled.Refresh, "refresh")
                        }
                    }
                },
                topBar = {
                    TopAppBar(title = {
                        Icon(Icons.Filled.TrendingUp, "trending up", modifier = Modifier.padding(end = 8.dp))
                        Text(stringResource(R.string.app_name))
                    })
                },
                bottomBar = {
                    BottomNavigation {
                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(screen.icon, stringResource(screen.resourceId)) },
                                label = { Text(stringResource(screen.resourceId)) },
                                selected = currentRoute == screen.route,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo = navController.graph.startDestination
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    composable(Screen.Home.route) { Home(total, assets) }
                    composable(Screen.Accounts.route) { Accounts(accounts) }
                }
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Activity(applicationContext) }
    }
}