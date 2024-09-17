package dev.janssenbatista.moneytracker.daos

import androidx.room.Dao
import androidx.room.Query
import dev.janssenbatista.moneytracker.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM tb_categories WHERE id = 1")
    fun getAllIncomeCategories(): Flow<List<Category>>

    @Query("SELECT * FROM tb_categories WHERE id = 2")
    fun getAllExpenseCategories(): Flow<List<Category>>
}