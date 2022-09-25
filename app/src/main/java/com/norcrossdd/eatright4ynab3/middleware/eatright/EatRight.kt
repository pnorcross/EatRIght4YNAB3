package com.norcrossdd.eatright4ynab3.middleware.eatright
import android.content.res.Resources.NotFoundException
import com.norcrossdd.eatright4ynab3.apis.eatright.*

data class CalorieRecord(
    override var description : String,
    override var calories: Int
    ) : ICalorieRecord {

}

data class BudgetCategory(
    override var name : String,
    override var calorieRecords: MutableList<ICalorieRecord>
    ) : IBudgetCategory {

}

class EatRight : IEatRight {
    override val calorieRecord : Map<String,IBudgetCategory> = mapOf<String, IBudgetCategory>(
        "Breakfast" to BudgetCategory("Breakfast", mutableListOf()),
        "Lunch"     to BudgetCategory("Lunch",     mutableListOf()),
        "Dinner"    to BudgetCategory("Dinner",    mutableListOf()),
        "Snacks"    to BudgetCategory("Snacks",    mutableListOf())
    )
    override var bmr : Int = 2000
        set(input : Int) { if(input > 0 && input <= 20000) { field = input } }

    override fun addCalorieRecord (budgetCategoryName : String, calorieRecord : ICalorieRecord) {
        this.calorieRecord[budgetCategoryName]?.calorieRecords?.add(calorieRecord) ?:
         throw NotFoundException("Tried to add calorie record to unknown category: $budgetCategoryName")
    }

    override fun removeCalorieRecord (budgetCategoryName: String, index: Int) {
        try {
            calorieRecord[budgetCategoryName]!!.calorieRecords.removeAt(index)
        } catch (e:java.lang.Exception) {
            throw NotFoundException("Tried to remove unknown calorie record: $budgetCategoryName,${index.toString()}")
        }

    }

}
