package com.douglas.financial.ui.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingDown
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

enum class Screen(val title: String, val imageVector: ImageVector) {
    Home("Home", Icons.Outlined.Home),
    Investments("Investimentos", Icons.AutoMirrored.Outlined.TrendingUp),
    Expenses("Despesas", Icons.AutoMirrored.Outlined.TrendingDown)
}

@Composable
fun FinancialTabBar(
    selectedTabIndex: Int,
    onTabSelected: (Int, Screen) -> Unit = {_,_ -> }
) {
    BottomAppBar {
        TabRow(selectedTabIndex = selectedTabIndex) {
            Screen.entries.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        onTabSelected(index, tab)
                    },
                    text = { Text(tab.title) },
                    icon = { Icon(tab.imageVector, contentDescription = tab.title) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinancialTabBarPreview() {
    FinancialTabBar(selectedTabIndex = 0)
}