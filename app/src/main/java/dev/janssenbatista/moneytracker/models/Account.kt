package dev.janssenbatista.moneytracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

@Entity(tableName = "tb_accounts")
data class Account(
    @PrimaryKey
    val id: Int?,
    val description: String,
    val amount: BigDecimal,
    @ColumnInfo("account_type")
    val accountType: Int,
    @ColumnInfo("show_in_total_balance")
    val showInTotalBalance: Boolean = true,
    @ColumnInfo("created_at")
    val createdAt: Date = Date(),
    @ColumnInfo("updated_at")
    val updatedAt: Date = Date()
)