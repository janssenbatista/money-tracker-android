package dev.janssenbatista.moneytracker.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.janssenbatista.moneytracker.R
import dev.janssenbatista.moneytracker.daos.AccountDao
import dev.janssenbatista.moneytracker.daos.CategoryDao
import dev.janssenbatista.moneytracker.database.converters.AmountConverter
import dev.janssenbatista.moneytracker.database.converters.CategoryTypeConverter
import dev.janssenbatista.moneytracker.database.converters.DataConverter
import dev.janssenbatista.moneytracker.models.Account
import dev.janssenbatista.moneytracker.models.AccountType
import dev.janssenbatista.moneytracker.models.Category
import java.util.Date

@Database(
    entities = [Account::class, Category::class],
    version = 2,
    autoMigrations = [AutoMigration(1, 2)],
    exportSchema = true
)
@TypeConverters(DataConverter::class, AmountConverter::class, CategoryTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao

    class AddAccountCallback(private val context: Context) :
        Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val accountName = context.getString(R.string.wallet)
            val createdAt = Date().time
            val updatedAt = Date().time
            db.execSQL(
                """INSERT INTO tb_accounts 
                VALUES 
                (1, '$accountName', 0.0, ${AccountType.WALLET.id}, 1, $createdAt, $updatedAt)"""
                    .trimMargin()
            )
        }
    }

    class AddCategoriesCallBack(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val date = Date()
            context.resources.getStringArray(R.array.income_categories).forEach { description ->
                db.execSQL("INSERT INTO tb_categories VALUES (null, '$description', 1, ${date.time})")
            }
            context.resources.getStringArray(R.array.expense_categories).forEach { description ->
                db.execSQL("INSERT INTO tb_categories VALUES (null, '$description', 2, ${date.time})")
            }
        }
    }
}