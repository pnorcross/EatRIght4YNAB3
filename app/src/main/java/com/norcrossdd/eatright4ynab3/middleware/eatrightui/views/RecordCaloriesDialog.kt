package com.norcrossdd.eatright4ynab3.middleware.eatrightui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import com.norcrossdd.eatright4ynab3.apis.eatright.IBudgetCategory
import com.norcrossdd.eatright4ynab3.apis.eatrightui.views.IRecordCaloriesDialog
import com.norcrossdd.eatright4ynab3.middleware.eatright.EatRight

class RecordCaloriesDialog : IRecordCaloriesDialog{
    private object model : ViewModel() {
        var recordingCalories by mutableStateOf(false)
    }
    var todaysCalories : Map<String, IBudgetCategory>

    init {
        todaysCalories = EatRight().calorieRecord
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun create() : () -> Unit {
        if(model.recordingCalories) {
            AlertDialog(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(.9f),
                properties = DialogProperties(usePlatformDefaultWidth = false),
                onDismissRequest = { /*TODO*/ },
                buttons = {
                    RecordCaloriesDialogButtons()
                          },
                text = {
                    RecordCaloriesDialogContents()
                }
            )
        }

        return {
            model.recordingCalories = true
        }
    }


    @Composable fun RecordCaloriesDialogButtons() {
        Divider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AlertDialogButton("Confirm",
                modifier = Modifier.weight(1f)
            ) { model.recordingCalories = false }

            Divider( Modifier.height(20.dp).width(1.dp) )

            AlertDialogButton("Cancel",
                modifier = Modifier.weight(1f)
            ) { model.recordingCalories = false }
        }
    }

    @Composable fun RecordCaloriesDialogContents () {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.height(10.dp)) {
                Text("")
            }


            CalorieRecordCalorieField()
            Spacer(Modifier.height(15.dp))

            CalorieRecordDescriptionField()
            Spacer(modifier = Modifier.height(15.dp))

            CalorieRecordCategoryField().create()

        }
    }

    @Composable fun CalorieRecordCalorieField() {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                CalorieRecordCalorieFieldTextField()
                Divider(color = Color.Black, modifier = Modifier.height(2.dp))
            }

        }
    }

    @Composable fun CalorieRecordCalorieFieldTextField(){
        var caloriesToRecordString : String by remember { mutableStateOf("0") }
        BasicTextField(
            value = caloriesToRecordString.toString(),
            onValueChange = { caloriesToRecordString = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle( fontSize = 40.sp, textAlign = TextAlign.Center),
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .onFocusChanged {
                    caloriesToRecordString = (
                            try {
                                caloriesToRecordString.toInt()
                            } catch (e: Exception) {
                                0
                            }).toString()
                }

        )
    }

    @Composable fun CalorieRecordDescriptionField() {
        var caloriesDescription : String by remember    { mutableStateOf("Food") }
        TextField(value = caloriesDescription,
            singleLine = true,
            onValueChange = { caloriesDescription = it },
            label = { Text("Description") })
    }

    private class CalorieRecordCategoryField {
        private object model : ViewModel() {
            var expanded : Boolean by mutableStateOf(false)
            var selectedIndex : Int by mutableStateOf(0)
        }

        @Composable fun create(){
            CalorieRecordCategoryFieldTextField()
            CalorieRecordCategoryFieldDropdownMenu()
        }

        @Composable fun CalorieRecordCategoryFieldTextField() {
            val currentHighlightColor = (if (model.expanded) MaterialTheme.colors.primary else Color.DarkGray)
            TextField(value = EatRight().budgetCategories[model.selectedIndex],
                enabled = false,
                onValueChange = {},
                label = { Text("Category") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray,
                    disabledLabelColor = currentHighlightColor,
                    disabledIndicatorColor = currentHighlightColor
                ),
                modifier = Modifier.clickable() { model.expanded = true }
            )
        }

        @Composable fun CalorieRecordCategoryFieldDropdownMenu() {
            DropdownMenu(
                expanded = model.expanded,
                onDismissRequest = { model.expanded = false },
                modifier = Modifier
            ) {
                EatRight().budgetCategories.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        model.selectedIndex = index
                        model.expanded = false
                    }) {
                        Text(s)
                    }
                }
            }
        }

    }

    @Composable fun AlertDialogButton(text: String, modifier: Modifier, action: () -> Unit) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .clickable { action() }
                .padding(5.dp)) {
            Text(text, fontSize = 20.sp)
        }
    }

}