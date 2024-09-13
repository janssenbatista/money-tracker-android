package dev.janssenbatista.moneytracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(tableName = "tb_accounts")
data class Account(
    @PrimaryKey
    val id: Int?,
    val description: String,
    val amount: BigDecimal,
    @ColumnInfo("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo("updated_at")
    val updatedAt: LocalDateTime
)