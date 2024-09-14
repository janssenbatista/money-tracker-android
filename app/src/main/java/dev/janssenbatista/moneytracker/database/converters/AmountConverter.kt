package dev.janssenbatista.moneytracker.database.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

class AmountConverter {

    @TypeConverter
    fun fromDouble(amount: Double?): BigDecimal? {
        return amount?.let {
            BigDecimal.valueOf(it)
        } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun toDouble(amount: BigDecimal?): Double? {
        return amount?.toDouble()
    }
}