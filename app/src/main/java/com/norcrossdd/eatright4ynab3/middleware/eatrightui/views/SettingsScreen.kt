package com.norcrossdd.eatright4ynab3.middleware.eatrightui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.norcrossdd.eatright4ynab3.apis.eatrightui.views.ISettingsScreen


class SettingsScreen : ISettingsScreen{
    var bmrText : String = "2000"
    var personalAccessToken : String = ""
    @Composable override fun create() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Section("User Settings",
                { BmrField() },
                { PatField() }
            )
        }
    }

    @Composable private fun Section(title : String, vararg items : @Composable() () -> Unit) {
        Text(title, fontSize = 24.sp)
        Divider()
        Spacer(modifier = Modifier.height(10.dp))

        items.forEach {
            it()
            Spacer(modifier = Modifier.height(15.dp))
        }
    }

    @Composable
    fun BmrField() {
        var text by remember { mutableStateOf(bmrText) }
        TextField(value = text,
            onValueChange = { text = it;  bmrText = it },
            singleLine = true,
            label = { Text("Basal Metabolic Rate (BMR)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }

    @Composable
    fun PatField() {
        var text by remember { mutableStateOf(personalAccessToken) }
        TextField(value = text,
            onValueChange = { text = it;  personalAccessToken = it },
            singleLine = true,
            label = { Text("Personal Access Token") },
            visualTransformation = PasswordVisualTransformation()
        )
    }

}