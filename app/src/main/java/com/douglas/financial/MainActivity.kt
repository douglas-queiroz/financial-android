package com.douglas.financial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.douglas.financial.feature.expense.list.ExpensesScreen
import com.douglas.financial.feature.home.HomeScreen
import com.douglas.financial.ui.componentes.FinancialTabBar
import com.douglas.financial.ui.componentes.Screen
import com.douglas.financial.ui.theme.FinancialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var selectedTabIndex by remember { mutableStateOf(0) }
            val bottomBar: @Composable () -> Unit = {
                FinancialTabBar(
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { index, tab ->
                        selectedTabIndex = index

                        navController.navigate(tab.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            FinancialTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.name,
                ) {
                    composable(Screen.Home.name) {
                        HomeScreen(bottomBar = bottomBar)
                    }
                    composable(Screen.Investments.name) {
                        HomeScreen(bottomBar = bottomBar)
                    }
                    composable(Screen.Expenses.name) {
                        ExpensesScreen(bottomBar = bottomBar)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinancialTheme {
        HomeScreen()
    }
}