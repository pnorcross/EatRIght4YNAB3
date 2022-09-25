package com.norcrossdd.eatright4ynab3.apis.eatrightui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface IRouter {
    @Composable fun create(navController: NavHostController)
}