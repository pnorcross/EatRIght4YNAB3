package com.norcrossdd.eatright4ynab3.apis.eatright

interface IEatRight {
    val calorieRecord : Map<String,IBudgetCategory>
    var bmr : Int
    val remainingCalories : Int
        get() = bmr - calorieRecord.values.sumOf {
            it.calorieRecords.sumOf { it.calories }
        }
    val budgetCategories : List<String>
        get() {
            return calorieRecord.keys.toList()
        }

    fun addCalorieRecord (budgetCategoryName : String, calorieRecord : ICalorieRecord)
    fun removeCalorieRecord (budgetCategoryName: String, index: Int)
}

interface ICalorieRecord {
    var description : String
    var calories : Int
}

interface IBudgetCategory {
    var name : String
    var calorieRecords : MutableList<ICalorieRecord>
}

