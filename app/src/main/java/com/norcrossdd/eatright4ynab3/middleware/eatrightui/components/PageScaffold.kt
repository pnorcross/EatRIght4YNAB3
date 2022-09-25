package com.norcrossdd.eatright4ynab3.middleware.eatrightui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PageScaffold : IPageScaffold {
    private var scaffoldState : ScaffoldState? = null
    private var scope : CoroutineScope? = null
    private var navController : NavHostController? = null

    @Composable override fun create () {
        scaffoldState = rememberScaffoldState()
        scope = rememberCoroutineScope()
        navController = rememberNavController()

        Scaffold(
            scaffoldState = scaffoldState!!,
            topBar = { MenuBar() },
            drawerContent = { Drawer() },
            drawerGesturesEnabled = false
        ) {
            Column() {
                Router().create(navController!!)
            }
        }
    }


    @Composable
    private fun MenuBar() {
        TopAppBar (
            title = { Text(text = "Eat Right 4 YNAB") },
            backgroundColor = Color(0xFF22AAFF),
            contentColor = Color.White,
            navigationIcon = {
                IconButton(
                    onClick = { openDrawer() }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            }
        )
    }

    fun openDrawer() {
        scope!!.launch{
            scaffoldState!!.drawerState.apply {
                open()
            }
        }
    }

    fun closeDrawer() {
        scope!!.launch{
            scaffoldState!!.drawerState.apply {
                close()
            }
        }
    }

    @Composable
    private fun Drawer() {
        Spacer(modifier = Modifier.height(30.dp))
        IconDrawerLink(
            route = "home",
            icon = Icons.Filled.Home,
            text = "Home"
        )
        IconDrawerLink(
            route = "settings",
            icon = Icons.Filled.Settings,
            text = "Settings"
        )
        DrawerBackLink();
    }

    @Composable
    private fun IconDrawerLink (route: String, text: String, icon: ImageVector) {
        DrawerLink(
            route = route
        ) {
            Icon(icon, contentDescription = null)
            Text("  $text", fontSize = 24.sp)
        }
    }

    @Composable
    private fun DrawerLink( route: String, content: @Composable() () -> Unit) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    navController!!.navigate(route)
                    closeDrawer()
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            ) {
                Column ( horizontalAlignment = Alignment.CenterHorizontally ){
                    content()
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                }
            }
        }
    }

    @Composable fun DrawerBackLink() {
        IconButton(onClick = { closeDrawer() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = null)
        }
    }

}