package dev.janssenbatista.moneytracker.models

import dev.janssenbatista.moneytracker.R

enum class CategoryType(val id: Int, val description: Int) {
    INCOME(1, R.string.income),
    EXPENSE(2, R.string.expense);

    companion object {
        fun getCategoryTypeById(id: Int) = CategoryType.entries.first {
            it.id == id
        }
    }
}