package com.norcrossdd.eatright4ynab3.middleware.eatrightui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.norcrossdd.eatright4ynab3.apis.eatrightui.components.IRouter
import com.norcrossdd.eatright4ynab3.middleware.eatrightui.views.HomeScreen
import com.norcrossdd.eatright4ynab3.middleware.eatrightui.views.SettingsScreen


class Router : IRouter{

    @Composable override fun create(navController: NavHostController) {

            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable(route = "home") {
                    HomeScreen().create()
                }
                composable(route = "settings") {
                    SettingsScreen().create()
                }
            }
    }

}