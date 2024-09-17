package dev.janssenbatista.moneytracker.database.converters

import androidx.room.TypeConverter
import dev.janssenbatista.moneytracker.models.CategoryType

class CategoryTypeConverter {

    @TypeConverter
    fun fromInt(id: Int): CategoryType {
        return id.let { categoryId ->
            CategoryType.getCategoryTypeById(categoryId)
        }
    }

    @TypeConverter
    fun toInt(categoryType: CategoryType): Int {
        return categoryType.id
    }
}