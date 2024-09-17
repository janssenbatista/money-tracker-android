package dev.janssenbatista.moneytracker.repositories

import dev.janssenbatista.moneytracker.daos.CategoryDao
import dev.janssenbatista.moneytracker.database.AppDatabase

class CategoryRepository(appDatabase: AppDatabase) {

    private val categoryDao: CategoryDao = appDatabase.categoryDao()

    fun getAllIncomeCategories() = categoryDao.getAllIncomeCategories()

    fun getAllExpenseCategories() = categoryDao.getAllExpenseCategories()
}