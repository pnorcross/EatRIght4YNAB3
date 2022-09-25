package com.norcrossdd.eatright4ynab3.middleware.eatrightui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.norcrossdd.eatright4ynab3.apis.eatright.IBudgetCategory
import com.norcrossdd.eatright4ynab3.apis.eatright.ICalorieRecord
import com.norcrossdd.eatright4ynab3.apis.eatrightui.views.IHomeScreen
import com.norcrossdd.eatright4ynab3.middleware.eatright.EatRight

class HomeScreen : IHomeScreen{
    var caloriesRemaining : Int = 2000
    var todaysCalories : Map<String, IBudgetCategory>

    init {
        todaysCalories = EatRight().calorieRecord
    }

    @Composable override fun create() {

        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ){

            Column (
                modifier = Modifier.fillMaxWidth(),
            ) {
                CaloriesRemainingHeader()
                CaloriesRecord()
            }


            RecordCaloriesButton(
                openDialogAction =  RecordCaloriesDialog().create()
            )
        }

    }

    @Composable fun CaloriesRemainingHeader() {

        caloriesRemaining = EatRight().remainingCalories;

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                caloriesRemaining.toString(),
                fontSize = 100.sp,
                color = Color.Green
            )
            Text("Calories Remaining", fontSize = 24.sp)
        }
    }


    @Composable fun CaloriesRecord() {
            Spacer(modifier = Modifier.height(30.dp))
            Column(horizontalAlignment = Alignment.Start){
                todaysCalories!!.values.forEach {
                    CaloriesRecordCategoryEntry(it)
                }
            }
    }

    @Composable fun CaloriesRecordCategoryEntry(category: IBudgetCategory) {
        Text(category.name, fontSize = 28.sp)
        Divider()
        Spacer(modifier = Modifier.height(5.dp))
        category.calorieRecords.forEach {
            CaloriesRecordCalorieEntry(it)
        }
        Spacer(modifier = Modifier.height(15.dp))
    }

    @Composable fun CaloriesRecordCalorieEntry(calorieRecord: ICalorieRecord) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("${calorieRecord.description}:")
            Text(calorieRecord.calories.toString())

        }
        Divider()
    }

    @Composable fun RecordCaloriesButton(openDialogAction : () -> Unit) {
        Box (
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            ExtendedFloatingActionButton(
                text = {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Text("Record Calories")
                },
                onClick = {
                    openDialogAction()
                }
            )
        }
    }


}