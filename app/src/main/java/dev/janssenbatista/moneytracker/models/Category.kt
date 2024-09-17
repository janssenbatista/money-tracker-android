package dev.janssenbatista.moneytracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tb_categories")
data class Category(
    @PrimaryKey
    val id: Int?,
    @ColumnInfo(index = true)
    val description: String,
    val categoryType: CategoryType,
    val createdAt: Date = Date()
)